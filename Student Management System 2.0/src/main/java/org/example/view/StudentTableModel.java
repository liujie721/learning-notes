package org.example.view;

import org.example.entity.Student;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    // 表格列名
    public final String[] columnNames = {"学号", "姓名", "年龄", "性别", "专业", "电话"};

    // 数据源
    private List<Student> studentList;

    // 无参构造
    public StudentTableModel() {
        this.studentList = new ArrayList<>();
    }

    // 有参构造
    public StudentTableModel(List<Student> studentList) {
        this.studentList = studentList;
    }

    // 刷新数据
    public void refreshData(List<Student> newData) {
        this.studentList = newData == null ? new ArrayList<>() : newData;
        fireTableDataChanged(); // 通知表格刷新
    }

    // 获取表格行数
    @Override
    public int getRowCount() {
        return studentList == null ? 0 : studentList.size();
    }

    // 获取表格列数
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // 获取列名
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // 获取单元格值
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = studentList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return student.getId();      // 第0列：学号
            case 1:
                return student.getName();    // 第1列：姓名
            case 2:
                return student.getAge();     // 第2列：年龄
            case 3:
                return student.getGender();  // 第3列：性别
            case 4:
                return student.getMajor();   // 第4列：专业
            case 5:
                return student.getPhone();   // 第5列：电话
            default:
                return null;
        }
    }
}