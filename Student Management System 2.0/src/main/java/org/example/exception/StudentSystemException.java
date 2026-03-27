package org.example.exception;

// 改为运行时异常，简化调用方处理
public class StudentSystemException extends RuntimeException {
    public StudentSystemException(String message) {
        super(message);
    }

    public StudentSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}