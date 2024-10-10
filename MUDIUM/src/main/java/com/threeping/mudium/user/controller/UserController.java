package com.threeping.mudium.user.controller;

import com.threeping.mudium.common.ResponseDTO;
import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.user.aggregate.dto.UserDTO;
import com.threeping.mudium.user.aggregate.vo.RequestRegistUserVO;
import com.threeping.mudium.user.aggregate.vo.ResponseUserVO;
import com.threeping.mudium.user.service.EmailVerificationService;
import com.threeping.mudium.user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

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

        /* 설명. 이메일 인증 */
        emailVerificationService.sendVerificationCode(newUser.getEmail());

        UserDTO savedUserDTO = userService.registUser(newUser);

        ResponseUserVO responseUser = modelMapper.map(savedUserDTO, ResponseUserVO.class);

        return ResponseDTO.ok(responseUser);
    }

    @PostMapping("/verify-code")
    public ResponseDTO<?> verifyEmailCode(@RequestParam String email, @RequestParam String code){
        boolean isVerified = emailVerificationService.verifyCode(email, code);
        if(isVerified){
            return ResponseDTO.ok("이메일 인증이 완료되었습니다.");
        } else{
            return ResponseDTO.fail(new CommonException(ErrorCode.INVALID_VERIFICATION_CODE));
        }
    }


}
