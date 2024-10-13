package com.threeping.mudium.user.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService {
    @Override
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        return null;
    }
}
