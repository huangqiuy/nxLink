package com.example.test.entity.twohrattendance;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceReturnPojo {
    private List<UseAttendancePojo> data;
    private Integer errcode;
    private String errmsg;
}
