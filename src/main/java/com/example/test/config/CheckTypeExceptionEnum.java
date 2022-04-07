package com.example.test.config;

/**
 * @author nn
 */

public enum CheckTypeExceptionEnum {

    MISSING_CARD("月初"),
    LATE("暂未"),
    VACATION("创建"),
    COMPENSATORY_LEAVE("考勤"),
    ATTENDANCE("表");

    private String type;

    CheckTypeExceptionEnum(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }
}
