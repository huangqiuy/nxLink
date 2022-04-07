package com.example.test.config;

/**
 * @author HQY
 */
public enum ResultCodeEnum {
    //请求成功
    SUCCESS(200, "请求成功"),
    //参数错误
    PARAM_IS_INVALID(1001, "参数错误"),
    //HTTP请求失败,ticket失效
    REQUEST_HTTP_TICKET_INVALID(2001, "HTTP请求失败,ticket失效"),
    //HTTP请求失败
    REQUEST_HTTP_CLIENT_ERROR(2002, "HTTP请求失败"),
    //HTTP请求成功
    REQUEST_HTTP_SUCCESS(0, "HTTP请求成功");

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
