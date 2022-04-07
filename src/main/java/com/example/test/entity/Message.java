package com.example.test.entity;

import lombok.Data;

@Data
public class Message {
    private Integer code;
    private String message;
    private Object data;
}
