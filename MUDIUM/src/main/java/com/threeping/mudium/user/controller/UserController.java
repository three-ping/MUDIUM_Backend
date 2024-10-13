package com.threeping.mudium.user.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.user.aggregate.dto.UserDTO;
import com.threeping.mudium.user.aggregate.vo.RequestRegistUserVO;
import com.threeping.mudium.user.aggregate.vo.ResponseUserVO;
import com.threeping.mudium.user.service.EmailVerificationService;
import com.threeping.mudium.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Environment env;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final EmailVerificationService emailVerificationService;

    @Autowired
    public UserController(Environment env, ModelMapper modelMapper, UserService userService, EmailVerificationService emailVerificationService) {
        this.env = env;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.emailVerificationService = emailVerificationService;
    }
    @GetMapping("/health")
    public String healthCheck(){
        return "I'm working in user service " + env.getProperty("local.server.port");
    }

    @PostMapping("/normal")
    public ResponseDTO<?> registNormalUser(@RequestBody RequestRegistUserVO newUser){

        /* email verification */
        // send verification code via email
        emailVerificationService.sendVerificationCode(newUser.getEmail());

//        UserDTO savedUserDTO = userService.registUser(newUser);

//        ResponseUserVO responseUser = modelMapper.map(savedUserDTO, ResponseUserVO.class);

        return ResponseDTO.ok("이메일 인증을 완료해주세요");
    }



    @PostMapping("/verify-code")
    public ResponseDTO<?> verifyEmailCode(@RequestParam String email, @RequestParam String code, @RequestBody RequestRegistUserVO newUser){
        boolean isVerified = emailVerificationService.verifyCode(email, code);
        if(isVerified){
            UserDTO savedUserDTO = userService.registUser(newUser);

            ResponseUserVO responseUserVO = modelMapper.map(savedUserDTO, ResponseUserVO.class);
            return ResponseDTO.ok(responseUserVO);
        } else{
            return ResponseDTO.fail(new CommonException(ErrorCode.INVALID_VERIFICATION_CODE));
        }
    }



}
