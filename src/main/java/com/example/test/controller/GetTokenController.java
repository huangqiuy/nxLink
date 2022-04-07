package com.example.test.controller;

import com.example.test.service.IGetTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/getToken")
public class GetTokenController {

    @Resource
    private IGetTokenService getTokenService;

    @GetMapping
    public String getToken(String ticket, String userId) throws IOException {
        return getTokenService.getTokens(ticket, userId);
    }

}
