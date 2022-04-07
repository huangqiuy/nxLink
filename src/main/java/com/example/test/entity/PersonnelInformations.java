package com.example.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author hqy
 * @since 2022-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PersonnelInformations implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private Integer id;

    private String personId;

    private String name;

    private String mobile;

    private String empNo;

    private String departmentId;

    private String jobPositionId;

    private String jobLevelId;

    private String workPlaceId;

    private Integer workStatus;

    private Integer workType;

    private LocalDateTime hireDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String credentialsNo;

    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private LocalDateTime birthday;


}
