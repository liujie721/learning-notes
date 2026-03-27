package org.example.exception;


public class DataImportException extends StudentSystemException {


    public DataImportException(String message) {
        super(message);
    }


    public DataImportException(String message, Throwable cause) {
        super(message, cause);
    }


    public DataImportException(String errorType, int lineNum) {
        super("CSV导入失败：第" + lineNum + "行出现" + errorType + "，请检查文件格式！");
    }
}