// 文件: StudentService.java
package org.example.service;

import org.example.entity.Student;
import org.example.exception.StudentSystemException;

import java.util.List;

public interface StudentService {
    void addStudent(Student student) throws StudentSystemException; // 添加学生
    void removeStudent(String studentId) throws StudentSystemException; // 删除学生
    void updateStudent(Student student) throws StudentSystemException; // 修改学生（修正拼写）
    List<Student> getAllStudents() throws StudentSystemException; // 获取所有学生（修正拼写）
    List<Student> searchStudent(String id) throws StudentSystemException; // 搜索学生
    void importCSV(String filePath) throws StudentSystemException; // 批量导入（修正方法名）
}