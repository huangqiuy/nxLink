package com.example.test.entity.twohrtoken;

import lombok.Data;

@Data
public class HRToken {
    private Integer errCode;
    private HrTokenData data;
    private String errMsg;
}
