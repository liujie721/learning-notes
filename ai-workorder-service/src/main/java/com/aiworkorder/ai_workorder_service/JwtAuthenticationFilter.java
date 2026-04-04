package com.aiworkorder.ai_workorder_service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.io.IOException;

//JWT过滤器
@Component
public class JwtAuthenticationFilter {
    //注入JWT，生成，解析，校检token
    @Autowired
    private JwtUtil jwtUtil;//自动注入，jwt工具，获取token

    //去数据库查用户
    @Autowired
    private UserDetailsService userDetailsService;// UserDetailsService唯一作用：根据用户名查用户

    //请求前端接口
    protected void doFilterInternal(
            // HttpServletRequest获取前端传来的所有东西
            HttpServletRequest request,   // 请求对象：拿请求头、参数

            //HttpServletResponse后端 → 向前端返回数据、状态、提示信息
            HttpServletResponse response, // 响应对象

            FilterChain chain             // 过滤器链：放行用
    )throws ServletException, IOException{

        //向前端请求头里拿token
        String header = request.getHeader("Authorization");

        //判断token的存在
        if(header==null||!header.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);
        // 从 token 里解析出 用户名

        String username = jwtUtil.getToken(token);

        //SecurityContextHolder  Spring 存放登录状态的工具   getContext() 拿到当前请求的上下文  getAuthentication()
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user=userDetailsService.loadUserByUsername(username);
            if(jwtUtil.isToken(token)){

                // UsernamePasswordAuthenticationToken ：Spring Security 专用的 “登录凭证 / 通行证”
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                user,        // 用户信息
                                null,        // 密码不需要了
                                user.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}
