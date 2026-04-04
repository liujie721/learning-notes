package com.aiworkorder.ai_workorder_service;
//实体类，存数据
public class User {
    private String id;//用户的唯一标识
    private String username;//登入账号
    private String password;//登入密码
    private String role;//登入类型
    private String createtime;//创建时间

    //无参
    public void User(){
    }

    //有参
    public void User(String id,String username,String password,String role,String createtime){
        this.id=id;
        this.username=username;
        this.password=password;
        this.role=role;
        this.createtime=createtime;
    }

    //封装

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getCreatetime() {
        return createtime;
    }

    public String getRole() {
        return role;
    }
}
