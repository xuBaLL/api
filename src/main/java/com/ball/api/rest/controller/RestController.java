package com.ball.api.rest.controller;

import com.alibaba.fastjson.JSON;

import com.ball.api.rest.dto.MyRequestBody;
import com.ball.api.rest.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * @author xuball
 * @create 2024/6/29 21:40
 * @Description:TODO
 */
@Api(tags="接口服务端")
@Controller
@RequestMapping(value = "/rest")
public class RestController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TestService testService;


    /**
     *
     * rest接口
     */
    @ApiOperation(value="rest测试接口")
    @PostMapping(value = "testApi")
    @ResponseBody
    public ResponseEntity testApi(@RequestBody MyRequestBody requestBody, HttpServletRequest request) {
        logger.info("rest requestJosn\n"+requestBody.toString());
        String responseBodyStr  = testService.testApi(requestBody);
        logger.info("rest  responseBody\n"+responseBodyStr);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(responseBodyStr);

    }




}
