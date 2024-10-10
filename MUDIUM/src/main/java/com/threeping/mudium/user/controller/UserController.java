package com.threeping.mudium.user.controller;

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

    @Autowired
    public UserController(Environment env, ModelMapper modelMapper, UserService userService) {
        this.env = env;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }
    @GetMapping("/health")
    public String healthCheck(){
        return "I'm working in user service " + env.getProperty("local.server.port");
    }

//    @PostMapping("/users")
//    public ResponseDTO<?> registNormalUser(@RequestBody RequestRegistUserVO newUser){
//        UserDTO userDTO = modelMapper.map(newUser, UserDTO.class);
//
//
//
//    }
}
