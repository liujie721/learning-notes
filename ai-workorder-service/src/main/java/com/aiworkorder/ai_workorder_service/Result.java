package com.aiworkorder.ai_workorder_service;

import lombok.Data;

//泛型
@Data
public class Result<T> {
    private Integer code;//响应码
    private String message;//提示信息
    private T data;//数据
    private boolean success;//判断是否成功
    private Result() {}

    //成功无数据
    public static  <T> Result<T> success(){
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setSuccess(true);
        return r;
    }

    //成功有数据
    public static <T> Result<T> success(T data){
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMessage("操作成功");
        r.setData(data);
        r.setSuccess(true);
       return r;
    }

    //失败
    public static <T> Result<T> fail(String message) {
        Result<T> r = new Result<>();
        r.setCode(500);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }

    public static <T> Result<T> fail(Integer code, String message) {
        Result<T> r = new Result<>();
        r.setCode(code);
        r.setMessage(message);
        r.setSuccess(false);
        return r;
    }

}
