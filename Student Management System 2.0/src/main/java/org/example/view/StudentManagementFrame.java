package org.example.view;

import org.example.entity.Student;
import org.example.exception.StudentSystemException;
import org.example.service.StudentService;
import org.example.service.StudentServiceImpl;
import org.example.util.AiStudentTool;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class StudentManagementFrame extends JFrame {
    private StudentService studentService = new StudentServiceImpl();
    private StudentTableModel studentTableModel;
    private JTextField tId, tName, tAge, tGender, tMajor, tPhone;//单行输入格
    private JTable studentTable;

    //整体框架
    public StudentManagementFrame() {
        setTitle("吉首大学学生管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        loadStudentData();
    }

    public void initUI() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 5, 5));
        inputPanel.add(new JLabel("学号"));
        inputPanel.add(new JLabel("姓名"));
        inputPanel.add(new JLabel("年龄"));
        inputPanel.add(new JLabel("性别"));
        inputPanel.add(new JLabel("专业"));
        inputPanel.add(new JLabel("电话"));

        tId = new JTextField();
        tName = new JTextField();
        tAge = new JTextField();
        tGender = new JTextField();
        tMajor = new JTextField();
        tPhone = new JTextField();

        inputPanel.add(tId);
        inputPanel.add(tName);
        inputPanel.add(tAge);
        inputPanel.add(tGender);
        inputPanel.add(tMajor);
        inputPanel.add(tPhone);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("添加");
        JButton btnDelete = new JButton("删除");
        JButton btnUpdate = new JButton("修改");
        JButton btnSearch = new JButton("查询");
        JButton btnImportCSV = new JButton("导入CSV");
        JButton btnAiGenerate = new JButton("AI生成学生");
        JButton btnAiCheck = new JButton("AI信息校验");
        JButton btnAiAnalyze = new JButton("AI数据分析");

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnSearch);
        btnPanel.add(btnImportCSV);
        btnPanel.add(btnAiGenerate);
        btnPanel.add(btnAiCheck);
        btnPanel.add(btnAiAnalyze);

        studentTableModel = new StudentTableModel();
        studentTable = new JTable(studentTableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        setLayout(new BorderLayout(5, 5));
        add(inputPanel, BorderLayout.NORTH);
        add(btnPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        bindEvents(btnAdd, btnDelete, btnUpdate, btnSearch, btnImportCSV, btnAiGenerate, btnAiCheck, btnAiAnalyze);
    }

    private void bindEvents(JButton btnAdd, JButton btnDelete, JButton btnUpdate, JButton btnSearch, JButton btnImportCSV,
                            JButton btnAiGenerate, JButton btnAiCheck, JButton btnAiAnalyze) {
        btnAdd.addActionListener(e -> addStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnSearch.addActionListener(e -> searchStudent());
        btnImportCSV.addActionListener(e -> importCSV());
        btnAiGenerate.addActionListener(e -> aiGenerateStudent());
        btnAiCheck.addActionListener(e -> aiCheckStudent());
        btnAiAnalyze.addActionListener(e -> aiAnalyzeData());
    }

    private void addStudent() {
        try {
            String id = tId.getText().trim();
            String name = tName.getText().trim();
            String ageStr = tAge.getText().trim();
            String gender = tGender.getText().trim();
            String major = tMajor.getText().trim();
            String phone = tPhone.getText().trim();

            if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || major.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "所有字段不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setAge(Integer.parseInt(ageStr));
            student.setGender(gender);
            student.setMajor(major);
            student.setPhone(phone);

            studentService.addStudent(student);
            JOptionPane.showMessageDialog(this, "添加成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            loadStudentData();
            clearInput();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄必须是数字！", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (StudentSystemException e) {
            JOptionPane.showMessageDialog(this, "添加失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteStudent() {
        try {
            String id = tId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入要删除的学号！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            studentService.removeStudent(id);
            JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            loadStudentData();
            clearInput();

        } catch (StudentSystemException e) {
            JOptionPane.showMessageDialog(this, "删除失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateStudent() {
        try {
            String id = tId.getText().trim();
            String name = tName.getText().trim();
            String ageStr = tAge.getText().trim();
            String gender = tGender.getText().trim();
            String major = tMajor.getText().trim();
            String phone = tPhone.getText().trim();

            if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || major.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "所有字段不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Student student = new Student();
            student.setId(id);
            student.setName(name);
            student.setAge(Integer.parseInt(ageStr));
            student.setGender(gender);
            student.setMajor(major);
            student.setPhone(phone);

            studentService.updateStudent(student);
            JOptionPane.showMessageDialog(this, "修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
            loadStudentData();
            clearInput();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄必须是数字！", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (StudentSystemException e) {
            JOptionPane.showMessageDialog(this, "修改失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchStudent() {
        try {
            String id = tId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请输入要查询的学号！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Student> result = studentService.searchStudent(id);
            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未找到学号为" + id + "的学生！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                studentTableModel.refreshData(result);
                JOptionPane.showMessageDialog(this, "查询成功！共找到" + result.size() + "条记录", "成功", JOptionPane.INFORMATION_MESSAGE);
            }
            clearInput();

        } catch (StudentSystemException e) {
            JOptionPane.showMessageDialog(this, "查询失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            try {
                studentService.importCSV(filePath);
                JOptionPane.showMessageDialog(this, "CSV导入成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadStudentData();
            } catch (StudentSystemException e) {
                JOptionPane.showMessageDialog(this, "CSV导入失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadStudentData() {
        try {
            List<Student> students = studentService.getAllStudents();
            studentTableModel.refreshData(students);
        } catch (StudentSystemException ex) {
            JOptionPane.showMessageDialog(this, "加载数据失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearInput() {
        tId.setText("");
        tName.setText("");
        tAge.setText("");
        tGender.setText("");
        tMajor.setText("");
        tPhone.setText("");
    }

    // AI生成学生
    private void aiGenerateStudent() {
        try {
            Student student = AiStudentTool.aiGenerateStudent();
            tId.setText(student.getId());
            tName.setText(student.getName());
            tAge.setText(String.valueOf(student.getAge()));
            tGender.setText(student.getGender());
            tMajor.setText(student.getMajor());
            tPhone.setText(student.getPhone());
            JOptionPane.showMessageDialog(this, "AI已生成学生信息！");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "AI生成失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // AI校验学生信息
    private void aiCheckStudent() {
        try {
            String id = tId.getText().trim();
            String name = tName.getText().trim();
            String ageStr = tAge.getText().trim();
            String gender = tGender.getText().trim();
            String major = tMajor.getText().trim();
            String phone = tPhone.getText().trim();

            if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || gender.isEmpty() || major.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息后再校验！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Student s = new Student();
            s.setId(id);
            s.setName(name);
            s.setAge(Integer.parseInt(ageStr));
            s.setGender(gender);
            s.setMajor(major);
            s.setPhone(phone);

            String result = AiStudentTool.aiCheckStudent(s);
            JOptionPane.showMessageDialog(this, result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "年龄必须是数字！", "错误", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "AI校验失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // AI数据分析
    private void aiAnalyzeData() {
        try {
            List<Student> list = studentService.getAllStudents();
            String report = AiStudentTool.aiAnalyzeData(list);
            JOptionPane.showMessageDialog(this, report, "AI数据分析报告", JOptionPane.INFORMATION_MESSAGE);
        } catch (StudentSystemException e) {
            JOptionPane.showMessageDialog(this, "AI分析失败：" + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}