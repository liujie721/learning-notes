package org.example.util;

import org.example.config.SystemConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    // 复用SystemConfig的数据库常量，避免重复定义
    private static final String DB_DRIVER = SystemConfig.DB_DRIVER;
    private static final String DB_URL = SystemConfig.DB_URL;
    private static final String DB_USER = SystemConfig.DB_USER;
    private static final String DB_PASSWORD = SystemConfig.DB_PASSWORD;

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载MySQL驱动失败！", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(SystemConfig.ERROR_MSG_DB_CONNECT + "：" + e.getMessage(), e);
        }
    }
}