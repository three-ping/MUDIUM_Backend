package com.threeping.mudium.user.service;

import com.threeping.mudium.user.aggregate.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    void registUser(UserDTO userDTO);

    UserDTO getUserByUserId(String userIdentifier);
}
