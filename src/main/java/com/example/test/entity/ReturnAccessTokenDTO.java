package com.example.test.entity;

import lombok.Data;

/**
 * @author HQY
 */
@Data
public class ReturnAccessTokenDTO {
    private String accessToken;
    private String refreshToken;
    private String issuedTime;
    private long expireTime;
    private String clientSecret;
}
