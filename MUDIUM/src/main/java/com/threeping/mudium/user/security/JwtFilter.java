package com.threeping.mudium.user.security;


import com.threeping.mudium.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {   // 한번만 실행된다는 뜻

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /* 설명. RequestHeader.Authorization에 들고 온 JWT 토큰의 유효성 검사 및 인증(Authentication 객체로 관리)  */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("UsernamePasswordAuthenticationFilter보다 먼저 동작하는 필터, 이후 필터 동작 여부 결정");

        String authorizatonHeader = request.getHeader("Authorization");
        log.info("authorizatonHeader: {}", authorizatonHeader);

        /* 설명. JWT 토큰이 Request Header에 있는 경우(로그인 이후 요청의 경우) */
        if (authorizatonHeader != null && authorizatonHeader.startsWith("Bearer ")) {
            String token = authorizatonHeader.substring(7);     // "Bearer "을 제외한 뒷부분 추출

            log.info("token: {}", token);

            if(jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);

                log.info("JwtFilter를 통과한 유효한 토큰을 통해 security가 관리할 principal 객체: {}", authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);           // 인증돼서 이후 필터 생략
            }

        }
        /* 설명. 위의 if문으로 인증된 Authentication 객체가 principal 객체로 관리되지 않는다면 다음 필터 실행 */
        filterChain.doFilter(request, response);            // 실행 될 다음 필터는 UsernamePasswordAuthenticationFilter
    }
}