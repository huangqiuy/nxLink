package com.example.test.entity.workplustoken;

import lombok.Data;

@Data
public class UsersReturnDTO {
    private UsersResultDTO result;
    private String message;
    private Integer status;

}
