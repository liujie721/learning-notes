package org.example.service;

import org.example.config.SystemConfig;
import org.example.dao.StudentDAO;
import org.example.dao.StudentDAOImpl;
import org.example.entity.Student;
import org.example.exception.DataImportException;
import org.example.exception.DuplicateStudentException;
import org.example.exception.StudentNotFoundException;
import org.example.exception.StudentSystemException;
import org.example.util.CSVUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

    public StudentServiceImpl() {
        this.studentDAO = new StudentDAOImpl();
    }

    public StudentServiceImpl(StudentDAO studentDAO) {
        if (studentDAO == null) {
            throw new IllegalArgumentException("StudentDAO 不能为null");
        }
        this.studentDAO = studentDAO;
    }

    @Override
    public void addStudent(Student student) {
        if (student == null) {
            throw new StudentSystemException("学生信息不能为空");
        }
        String studentId = student.getId();
        if (studentDAO.select(studentId) != null) {
            throw new DuplicateStudentException("学号" + studentId + "已存在");
        }
        if (!studentDAO.insert(student)) {
            throw new StudentSystemException("添加学生失败");
        }
    }

    @Override
    public void removeStudent(String studentId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new StudentSystemException("学号不能为空");
        }
        if (studentDAO.select(studentId) == null) {
            throw new StudentNotFoundException("学号" + studentId + "不存在");
        }
        if (!studentDAO.delete(studentId)) {
            throw new StudentSystemException("删除学生失败");
        }
    }

    @Override
    public void updateStudent(Student student) {
        if (student == null) {
            throw new StudentSystemException("学生信息不能为空");
        }
        String studentId = student.getId();
        if (studentDAO.select(studentId) == null) {
            throw new StudentNotFoundException("学号" + studentId + "不存在");
        }
        if (!studentDAO.update(student)) {
            throw new StudentSystemException("更新学生失败");
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            return studentDAO.selectAll();
        } catch (Exception e) {
            throw new StudentSystemException("查询所有学生失败", e);
        }
    }

    @Override
    public List<Student> searchStudent(String id) {
        List<Student> result = new ArrayList<>();
        if (id == null || id.trim().isEmpty()) {
            return result;
        }
        try {
            Student student = studentDAO.select(id);
            if (student != null) {
                result.add(student);
            }
            return result;
        } catch (Exception e) {
            throw new StudentSystemException("搜索学生失败", e);
        }
    }

    @Override
    public void importCSV(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new DataImportException("CSV文件路径不能为空");
        }

        List<String[]> csvData;
        try {
            csvData = CSVUtil.readCSV(filePath);
            CSVUtil.checkCSVFormat(csvData); // 修复调用方式
        } catch (RuntimeException e) {
            throw new DataImportException("读取/校验CSV失败: " + e.getMessage(), e);
        }

        List<String> failMessages = new ArrayList<>();
        Connection conn = null;

        try {
            conn = studentDAO.getConnection();
            conn.setAutoCommit(false);

            for (int i = 0; i < csvData.size(); i++) {
                String[] row = csvData.get(i);
                try {
                    if (row.length != 6) {
                        throw new DataImportException("列数错误（必须6列）", i + 1);
                    }

                    Student student = new Student();
                    student.setId(row[0].trim());
                    student.setName(row[1].trim());
                    try {
                        student.setAge(Integer.parseInt(row[2].trim()));
                    } catch (NumberFormatException e) {
                        throw new DataImportException("年龄格式错误（非数字）", i + 1);
                    }
                    student.setGender(row[3].trim());
                    student.setMajor(row[4].trim());
                    student.setPhone(row[5].trim());

                    if (studentDAO.select(student.getId()) != null) {
                        throw new DuplicateStudentException("学号" + student.getId() + "已存在");
                    }

                    if (!studentDAO.insert(student)) {
                        throw new DataImportException("数据库插入失败", i + 1);
                    }

                } catch (StudentSystemException e) {
                    failMessages.add("第" + (i + 1) + "行：" + Arrays.toString(row) + " → " + e.getMessage());
                }
            }

            if (failMessages.isEmpty()) {
                conn.commit();
                System.out.println("CSV导入成功，事务提交");
            } else {
                conn.rollback();
                throw new DataImportException("CSV导入失败：" + String.join("；", failMessages));
            }

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                throw new StudentSystemException("事务回滚失败: " + ex.getMessage(), ex);
            }
            throw new DataImportException("数据库异常: " + e.getMessage(), e);

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                throw new StudentSystemException("关闭连接失败: " + e.getMessage(), e);
            }
        }
    }
}