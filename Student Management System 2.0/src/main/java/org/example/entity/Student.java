package org.example.entity;

import java.util.Objects;

public class Student {
    private String id;//学生学号
    private String name;//学生姓名
    private int age;//学生年龄
    private String gender;//学生性别
    private String major;//学生专业
    private String phone;//学生电话

    // 无参构造器（适用于反射、框架初始化等场景）
    public Student() {
    }

    // 全参构造器（方便快速创建学生对象）
    public Student(String id, String name, int age, String gender, String major, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.major = major;
        this.phone = phone;
    }

    //封装
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMajor() {
        return major;
    }

    public String getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //打印学生信息
    @Override
    public String toString() {
        return "Student{" +
                "学号='" + id + '\'' +
                ", 姓名='" + name + '\'' +
                ", 年龄=" + age +
                ", 性别='" + gender + '\'' +
                ", 专业='" + major + '\'' +
                ", 电话='" + phone + '\'' +
             '}';
    }

    //重写equals,判断学号是否相同
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    //hashCode重写,生成随机哈希值
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}