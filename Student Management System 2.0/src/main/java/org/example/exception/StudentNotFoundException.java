/**
 * 学生未找到异常
 * 职责：当根据学号/姓名等条件查询不到学生时抛出该异常
 * 继承：StudentSystemException（学生系统业务异常基类）
 */
package org.example.exception;

public class StudentNotFoundException extends StudentSystemException {


    public StudentNotFoundException(String message) {
        // 调用父类 StudentSystemException 的单参构造器
        super(message);
    }


    public StudentNotFoundException(String message, Throwable cause) {
        // 调用父类 StudentSystemException 的双参构造器
        super(message, cause);
    }
}