package com.example.test.entity.twohrattendance;

import lombok.Data;

@Data
public class UseAttendanceDTO {
    //主键ID
    private String id;
    //员工ID
    private String empId;
    //员工OA编码
    private String empOaCode;
    //员工姓名
    private String empName;
    //员工工号
    private String empNo;
    //考勤编号
    private String attendanceNo;
    //组织OA编码
    private String deptOaCode;
    //组织ID
    private String deptId;
    //组织名称
    private String depName;
    //岗位ID
    private String jobTitleId;
    //岗位名称
    private String jobTitleName;
    //应出勤天数
    private Integer expectedAttendDay;
    //应出勤小时数
    private Float expectedAttendHour;
    //工作时长(单位:小时)
    private Float workHour;
    //旷工天数
    private Integer absenteeismDay;
    //上班缺卡次数
    private Integer missingClockInCount;
    //下班缺卡次数
    private Integer missingClockOutCount;
    //缺卡次数
    private Integer missingClockCount;
    //补卡次数
    private Float leaveMarriageHour;
    //婚假小时
    private Integer mendClockCount;
    //短期病假小时
    private Float leaveAnnualHour;
    //年假小时
    private Float leaveSickHour;
    //调休假小时
    private Float leaveBusinessHour;
    //事假小时
    private Float leaveTimeOffHour;
    //产假小时
    private Float leavePaternityHour;
    //陪产假小时
    private Float leaveMaternityHour;
    //路途假小时
    private Float leaveHomeHour;
    //探亲假小时
    private Float leaveTripHour;
    //看护假小时
    private Float leaveNonAttendanceHour;
    //非出勤假小时
    private Float leaveCareHour;
    //产检假小时
    private Float leaveMourningHour;//丧假小时
    private Float leavePrenatalCheckupHour;
    private Float leaveLactationHour;//哺乳假小时
    private Float leaveType50Hour;//自定义假类-50（自定义假类来自2号考勤假期假类自定义配置），单位小时
    private Float leaveType51Hour;//自定义假类-51，单位小时
    private Float leaveMarriageDay;//婚假天
    private Float leaveMaternityDay;//产假天
    private Float leaveMourningDay;//丧假天
    private Float leaveHourCount;//请假总时长
    private Integer lateCount;//迟到次数
    private Float lateMinuteCount;//迟到分钟数
    private Integer absenteeismCount;//旷工次数
    private Integer earlyLeaveCount;//早退次数
    private Float leaveEarlyMinute;//早退分钟数
    private Float lateDeductMoney;//迟到扣款(元)
    private Float absenteeismDeductMoney;//旷工扣款(元)
    private Float earlyLeaveCharge;//早退扣款(元)
    private Float comprehensiveCharge;//综合扣款(元)
    private Float missingClockCharge;//缺卡扣款(元)
    private Float businessTripHour;//出差（小时）
    private Float businessTripDay;//出差天
    private Float fieldWorkHour;//外出（小时）
    private Float monthTotalOutingHour;//外勤合计（小时）

}
