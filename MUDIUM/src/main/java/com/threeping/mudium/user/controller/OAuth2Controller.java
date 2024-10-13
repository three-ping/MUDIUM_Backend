package com.threeping.mudium.user.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/api/users/oauth2")
public class OAuth2Controller {
    private ModelMapper modelMapper;
    private UserService userService;

    @Autowired
    public OAuth2Controller(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/kakao")
    public ResponseDTO<?> kakaoLogin(@RequestParam String code) {
        log.info("넘어온 카카오 코드: {}", code);

        return null;


    }
}
