package com.ball.api.rest.dto;

import com.alibaba.fastjson.annotation.JSONType;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xuball
 * @create 2024/6/29 0:23
 * @Description:TODO
 */
@JSONType(orders={
        "code",
        "msg"
})
@ToString
public class MyResponseBody implements Serializable {

    private static final long serialVersionUID = -1723282239304139979L;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
