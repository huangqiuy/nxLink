package com.example.test.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author hqy
 * @since 2022-03-22
 */
@RestController
@RequestMapping("/personnelInformations")
//@RequestMapping(value = "?", method = RequestMethod.GET)
public class PersonnelInformationsController {


//    public Object fun(@PathVariable String user_id, @PathVariable String ticket) {
//        return
//    }


//    @GetMapping
//    public String AppWork() {
//        return "app work!";
//    }

    //, String ticket, String user_id
//    @Resource
//    IPersonnelInformationsService iPersonnelInformationsService;
//
//    @GetMapping("/selectOneUser")
//    public Object selectOneUser(String workNumber, String ticket, String user_id) {
//        // 1.构造数据库对象
//        PersonnelInformations personnelInformations = new PersonnelInformations();
//        personnelInformations.setEmpNo(workNumber);
//        // 2.构造条件查询封装类
//        QueryWrapper<PersonnelInformations> queryWrapper = new QueryWrapper<>();
//        // where name = ${username}
//        queryWrapper.eq("emp_no", workNumber);
//        // 调用 service
//        PersonnelInformations one = iPersonnelInformationsService.getOne(queryWrapper);
//        return one.getPersonId();
//    }

    //    , String user_id
//     + "\n" + "user_id: " + user_id
    @GetMapping
    public String get(String ticket) {
        return "ticket: " + ticket;
    }

    @GetMapping("/attendanceByUser")
    public Object attendanceByUser(String ticket, String userId) {


    /*  需求：
        写一个接口，在nxLink门户显示二号人事部的考勤数据（平台数据对接）

        1.获取ticket和user_id
        ticket和user_id是前端传的，不需要单独写方法获取，只需要在调用接口是用代码来接收就可以。
        2.获取workPlus的token

        3.验证ticket

        4.验证用户

        5.获取person_id（查数据库）

        6.获取2号人事的token

        7.查询考勤信息

        NxLink（workPlus平台私有化部署）
        2号人事部
    */
        return null;
    }

}

