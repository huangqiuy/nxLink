package com.example.test.service;


import java.io.IOException;

/**
 * @author HQY
 */
public interface IGetTokenService {
    /**
     * 获取workPlus的token
     */
    String getTokens(String ticket, String userId) throws IOException;


    String getNewsList();
}
