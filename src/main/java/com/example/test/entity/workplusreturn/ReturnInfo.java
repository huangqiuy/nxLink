package com.example.test.entity.workplusreturn;

import lombok.Data;

/**
 * @author HQY
 */
@Data
public class ReturnInfo {
    private Integer status;
    private String message;
    private ResultInfo result;
}
