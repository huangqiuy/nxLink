package com.example.test.config;

/**
 * @author HQY
 **/

public enum ChekingTypeEnum {
    MISSING_CARD("缺卡"),
    LATE("迟到"),
    VACATION("休假"),
    COMPENSATORY_LEAVE("调休"),
    ATTENDANCE("出勤");

    private String type;

    ChekingTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
//
//    //缺卡
//    MISSING_CARD,
//    //迟到
//    LATE,
//    //休假
//    VACATION,
//    //调休
//    COMPENSATORY_LEAVE,
//    //出勤
//    ATTENDANCE

}
