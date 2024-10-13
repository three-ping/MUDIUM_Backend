package com.threeping.mudium.user.aggregate.vo;

import lombok.Data;

@Data
public class OAuth2LoginVO {
    private String userName;
    private String email;
    private String accessToken;
    private String refreshToken;
}
