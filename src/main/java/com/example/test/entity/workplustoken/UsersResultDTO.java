package com.example.test.entity.workplustoken;

import lombok.Data;

import java.util.List;

@Data
public class UsersResultDTO {
    private Integer total_count;
    private List<UserDTO> users;
}
