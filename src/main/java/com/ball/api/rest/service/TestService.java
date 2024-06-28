package com.ball.api.rest.service;

import com.alibaba.fastjson.JSON;
import com.ball.api.rest.dto.MyRequestBody;
import com.ball.api.rest.dto.MyResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author xuball
 * @create 2024/6/29 0:21
 * @Description:TODO
 */
@Service
public class TestService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public String testApi(MyRequestBody requestBody){
        MyResponseBody responseBody = new MyResponseBody();
        responseBody.setCode("1");//1成功 0失败
        // 构建校验返回报文
        if(StringUtils.isEmpty(requestBody.getOprName())){
            responseBody.setCode("0");
            responseBody.setMsg("操作人为空");
            logger.info("操作人为空");
            return JSON.toJSONString(responseBody);
        }else if(StringUtils.isEmpty(requestBody.getOrgName())){
            responseBody.setCode("0");
            responseBody.setMsg("机构名称为空");
            logger.info("机构名称为空");
            return JSON.toJSONString(responseBody);
        }

        responseBody.setMsg("操作成功");
        logger.info("操作成功");
        return JSON.toJSONString(responseBody);
    }
}
