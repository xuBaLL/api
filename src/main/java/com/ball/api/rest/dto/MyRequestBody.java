package com.ball.api.rest.dto;

import io.swagger.annotations.ApiModel;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xuball
 * @create 2024/6/29 0:23
 * @Description:TODO
 */
@ApiModel
@ToString
public class MyRequestBody implements Serializable {

    private static final long serialVersionUID = -7160405033152844962L;
    private String orgName;//机构名称
    private String oprName;//操作人

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOprName() {
        return oprName;
    }

    public void setOprName(String oprName) {
        this.oprName = oprName;
    }
}
