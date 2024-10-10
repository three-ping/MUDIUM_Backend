package com.threeping.mudium.user.service;

import com.threeping.mudium.common.exception.CommonException;
import com.threeping.mudium.common.exception.ErrorCode;
import com.threeping.mudium.user.aggregate.dto.UserDTO;
import com.threeping.mudium.user.aggregate.entity.UserEntity;
import com.threeping.mudium.user.aggregate.vo.RequestRegistUserVO;
import com.threeping.mudium.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository ,BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserEntity findByUserIdentifier(String userIdentifier) {
        return userRepository.findByUserIdentifier(userIdentifier);
    }

    @Override
    public UserDTO registUser(RequestRegistUserVO newUser) {
        UserEntity existingUser = userRepository.findByUserIdentifier("NORMAL_"+newUser.getUserAuthId());
        if(existingUser != null){
            throw new CommonException(ErrorCode.EXIST_USER_ID);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException{

        UserEntity loginUser = userRepository.findByUserIdentifier(userIdentifier);
        if(loginUser == null){
            throw new CommonException(ErrorCode.NOT_FOUND_USER_ID);
        }

        String encryptedPwd = loginUser.getEncryptedPwd();
        if (encryptedPwd == null) {
            encryptedPwd = "{noop}";
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return new User(loginUser.getUserAuthId(), encryptedPwd, true, true, true, true, grantedAuthorities);
    }


}
