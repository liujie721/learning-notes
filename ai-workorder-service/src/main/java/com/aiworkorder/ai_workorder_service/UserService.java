package com.aiworkorder.ai_workorder_service;

public interface UserService {
    Result<String> register(User user);
    Result<String> login(String username, String password);
}
