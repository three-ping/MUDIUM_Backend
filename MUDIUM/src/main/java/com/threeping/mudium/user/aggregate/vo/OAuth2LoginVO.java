package com.threeping.mudium.user.aggregate.vo;

import lombok.Data;

@Data
public class OAuth2LoginVO {
    private String id;
    private String userName;
    private String email;
    private String pwd;
    private String accessToken;
    private String refreshToken;
}
