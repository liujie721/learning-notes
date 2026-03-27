package org.example.config;

public class SystemConfig {
    // CSV表头标准（学号,姓名,年龄,性别,专业,电话）
    public static final String[] CSV_HEADER = {"学号", "姓名", "年龄", "性别", "专业", "电话"};

    // 错误提示常量
    public static final String ERROR_MSG_ID_DUPLICATE = "学号已存在！";
    public static final String ERROR_MSG_STUDENT_NOT_FOUND = "学生不存在！";
    public static final String ERROR_MSG_CSV_FORMAT = "CSV格式错误！";
    public static final String ERROR_MSG_DB_CONNECT = "数据库连接失败！";

    // 数据库配置（修改为自己的数据库信息）
    public static final String DB_URL = "jdbc:mysql://localhost:3306/practice_db?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8&allowPublicKeyRetrieval=true";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "123456";
    public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // HikariCP 连接池配置
    public static final int DB_POOL_MIN_IDLE = 5;
    public static final int DB_POOL_MAX_SIZE = 10;
    public static final long DB_POOL_IDLE_TIMEOUT = 300000;
    public static final long DB_POOL_CONN_TIMEOUT = 20000;
}