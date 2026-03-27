package org.example.dao;

import org.example.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

//接口，定义对学生数据的增删改查标准。
public interface StudentDAO {
    boolean insert(Student student); // 插入学生信息
    boolean delete(String id); // 删除学生信息
    boolean update(Student student); // 更新学生信息
    Student select(String id); // 根据学号查询学生
    Connection getConnection() throws SQLException; // 获取数据库连接
    List<Student> selectAll(); // 查询所有学生列表
}