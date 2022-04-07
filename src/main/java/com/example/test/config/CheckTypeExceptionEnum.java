package com.example.test.config;

/**
 * @author nn
 */

public enum CheckTypeExceptionEnum {

    MISSING_CARD("人事"),
    LATE("部门"),
    VACATION("未创"),
    COMPENSATORY_LEAVE("建考"),
    ATTENDANCE("勤表");

    private String type;

    CheckTypeExceptionEnum(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }
}
