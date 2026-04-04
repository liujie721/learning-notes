package com.aiworkorder.ai_workorder_service.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //
    @Override
    public UserDetails loadUserByUsername(String username)  {


        // 密码：123456
        String pass= new BCryptPasswordEncoder().encode("123456");

        // 返回 Security 标准用户
        //User:Spring Security 自带的官方工具类！帮你快速构造一个 UserDetails
        return User
                .withUsername("test")
                .password(pass)
                .roles("USER")  // 角色
                .build();
    }
}