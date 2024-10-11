package com.threeping.mudium.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /* Security 자체에서 사용할 암호화 방식 용 Bean 추가 */
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* RestTemplate Bean 추가 */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
