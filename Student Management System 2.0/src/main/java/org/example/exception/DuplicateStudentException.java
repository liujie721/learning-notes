/**
 * 学生学号重复异常
 * 职责：当添加/注册学生时，学号已存在（重复）时抛出该异常
 * 继承：StudentSystemException（学生系统业务异常基类）
 */
package org.example.exception;

public class DuplicateStudentException extends StudentSystemException {


    public DuplicateStudentException(String message) {
        // 调用父类 StudentSystemException 的单参构造器，传递异常消息
        super(message);
    }


    public DuplicateStudentException(String message, Throwable cause) {
        // 调用父类 StudentSystemException 的双参构造器，保留异常链
        super(message, cause);
    }
}