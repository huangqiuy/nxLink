package com.example.test.entity.workplustoken;

import lombok.Data;

@Data
public class UsersReturnPojo {
    private UsersResultPojo result;
    private String message;
    private Integer status;

}
