package com.example.test.entity.workplustoken;

import lombok.Data;

@Data
public class UserPojo {
    private Long birthday;
    private String gender;
    private Long create_time;
    private String initial;
    private Long modify_time;
    private Long activate_time;
    //    private Integer more_credentials;
//    private Integer platforms;
//    private Integer tags;
    private String domainId;
    private String pinyin;
    private String userId;
    private String name;
    private String nickname;
    //    private Integer tag_snapshots;
    private Boolean disabled;
    private String sourceId;
    private String state;
    private String email;
    private String status;
    private String username;
}
