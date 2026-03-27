package org.example.dao;

import org.example.config.SystemConfig;
import org.example.entity.Student;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//StudentDAOImpl = 学生数据的“数据库操作工具”负责：把学生信息 存进数据库 / 从数据库取出来 / 修改 / 删除
public class StudentDAOImpl implements StudentDAO {

    // 实现接口的getConnection方法
    @Override
    public Connection getConnection() throws SQLException {
        return DBUtil.getConnection();
    }

    // 添加学生
    @Override
    public boolean insert(Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO student (id, name, age, gender, major, phone) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getId());
            pstmt.setString(2, student.getName());
            pstmt.setInt(3, student.getAge());
            pstmt.setString(4, student.getGender());
            pstmt.setString(5, student.getMajor());
            pstmt.setString(6, student.getPhone());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            // 学号重复（MySQL 主键冲突）
            if (e.getSQLState() != null && e.getSQLState().equals("23505")) {
                throw new RuntimeException(SystemConfig.ERROR_MSG_ID_DUPLICATE + student.getId(), e);
            }
            throw new RuntimeException("添加学生失败", e);
        } finally {
            // 关闭资源
            closeResources(conn, pstmt, null);
        }
    }

    // 删除学生
    @Override
    public boolean delete(String studentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM student WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("删除学生失败", e);
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    // 更新学生
    @Override
    public boolean update(Student student) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getConnection();
            String sql = "UPDATE student SET name=?, age=?, gender=?, major=?, phone=? WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getName());
            pstmt.setInt(2, student.getAge());
            pstmt.setString(3, student.getGender());
            pstmt.setString(4, student.getMajor());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getId());
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("更新学生失败", e);
        } finally {
            closeResources(conn, pstmt, null);
        }
    }

    // 根据ID查询
    @Override
    public Student select(String studentId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM student WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, studentId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("major"),
                        rs.getString("phone")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("查询学生失败", e);
        } finally {
            closeResources(conn, pstmt, rs);
        }
    }

    // 查询所有
    @Override
    public List<Student> selectAll() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Student> studentList = new ArrayList<>();
        try {
            conn = getConnection();
            String sql = "SELECT * FROM student";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("gender"),
                        rs.getString("major"),
                        rs.getString("phone")
                );
                studentList.add(student);
            }
            return studentList;

        } catch (SQLException e) {
            throw new RuntimeException("查询全部学生失败", e);
        } finally {
            closeResources(conn, pstmt, rs);
        }
    }

    // 工具方法：关闭数据库资源
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("关闭数据库资源失败", e);
        }
    }
}