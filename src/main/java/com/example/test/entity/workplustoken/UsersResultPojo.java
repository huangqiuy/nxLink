package com.example.test.entity.workplustoken;

import lombok.Data;

import java.util.List;

@Data
public class UsersResultPojo {
    private Integer total_count;
    private List<UserPojo> users;
}
