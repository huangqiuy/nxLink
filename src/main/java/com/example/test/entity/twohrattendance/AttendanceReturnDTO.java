package com.example.test.entity.twohrattendance;

import lombok.Data;

import java.util.List;

@Data
public class AttendanceReturnDTO {
    private List<UseAttendanceDTO> data;
    private Integer errcode;
    private String errmsg;
}
