package org.example.util;

import org.example.entity.Student;
import java.util.*;

import static java.lang.Math.random;

public class AiStudentTool {

    private static final Random random = new Random();

    // AI 自动生成一条学生信息
    public static Student aiGenerateStudent() {
        String[] surnames = {"李", "王", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴"};
        String[] names = {"伟", "芳", "娜", "敏", "静", "杰", "强", "磊", "洋", "超"};
        String[] majors = {"计算机科学与技术", "软件工程", "数据科学与大数据", "人工智能", "电子信息"};
        String[] genders = {"男", "女"};
        String[] year={"2022","2023","2024","2025"};
        String id = year[random.nextInt(year.length)] +  (100000 + random.nextInt(9999))+"";
        String name = surnames[random.nextInt(surnames.length)] + names[random.nextInt(names.length)];
        int age = 18 + random.nextInt(6);
        String gender = genders[random.nextInt(genders.length)];
        String major = majors[random.nextInt(majors.length)];
        String phone = "138" + (10000000 + random.nextInt(90000000));

        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        student.setGender(gender);
        student.setMajor(major);
        student.setPhone(phone);
        return student;
    }

    //  AI 智能校验学生信息
    public static String aiCheckStudent(Student student) {
        if (student.getId() == null || student.getId().length() < 8) {
            return "AI校验失败：学号格式不正确";
        }
        if (student.getName() == null || student.getName().length() < 2) {
            return "AI校验失败：姓名格式不正确";
        }
        if (student.getAge() < 15 || student.getAge() > 30) {
            return "AI校验失败：年龄必须在15-30岁之间";
        }
        if (student.getPhone() == null || student.getPhone().length() != 11) {
            return "AI校验失败：手机号必须是11位";
        }
        return "AI校验通过：信息合法";
    }

    // AI 数据分析（生成报告）
    public static String aiAnalyzeData(List<Student> list) {
        if (list == null || list.isEmpty()) {
            return "暂无数据可分析";
        }

        long male = list.stream().filter(s -> "男".equals(s.getGender())).count();
        long female = list.size() - male;
        double avgAge = list.stream().mapToInt(Student::getAge).average().orElse(0);

        return "AI 学生数据分析报告\n" +
                "总人数：" + list.size() + "\n" +
                "男生：" + male + "人\n" +
                "女生：" + female + "人\n" +
                "平均年龄：" + String.format("%.1f", avgAge) + "岁";
    }
}