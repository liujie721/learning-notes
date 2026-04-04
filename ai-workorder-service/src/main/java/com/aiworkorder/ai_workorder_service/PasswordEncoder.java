package com.aiworkorder.ai_workorder_service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//配置类
@Configuration
public class PasswordEncoder {
   public BCryptPasswordEncoder bCryptPasswordEncoder(){
       return new BCryptPasswordEncoder();//加密工具
    }
}
