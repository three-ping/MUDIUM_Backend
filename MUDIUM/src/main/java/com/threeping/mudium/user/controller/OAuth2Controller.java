package com.threeping.mudium.user.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.user.aggregate.dto.UserDTO;
import com.threeping.mudium.user.aggregate.vo.OAuth2LoginVO;
import com.threeping.mudium.user.service.OAuth2UserService;
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
    private OAuth2UserService oAuth2UserService;

    @Autowired
    public OAuth2Controller(ModelMapper modelMapper
            , UserService userService
            , OAuth2UserService oAuth2UserService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.oAuth2UserService = oAuth2UserService;
    }

    @GetMapping("/kakao")
    public ResponseDTO<?> kakaoLogin(@RequestParam String code) {
        log.info("넘어온 카카오 코드: {}", code);

        OAuth2LoginVO user = oAuth2UserService.processKakaoUser(code);
        log.info("userDTO: {}", user);
        return ResponseDTO.ok(user);


    }
}
