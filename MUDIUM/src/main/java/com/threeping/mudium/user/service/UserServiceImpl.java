package com.threeping.mudium.user.service;

import com.threeping.mudium.user.aggregate.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Override
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UserDTO getUserByUserId(String memNo) {
        return null;
    }
    @Transactional
    @Override
    public void registUser(UserDTO userDTO) {

    }

}
