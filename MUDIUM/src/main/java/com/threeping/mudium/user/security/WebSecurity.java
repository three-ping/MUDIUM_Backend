package com.threeping.mudium.user.security;

import com.threeping.mudium.user.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration

/* 설명. 기능 개발중 시큐리티 적용 ON/OFF */
//@EnableWebSecurity
public class WebSecurity {
private BCryptPasswordEncoder bCryptPasswordEncoder;
private UserService userService;
private Environment env;

}
