package com.mall.furniture.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xieyongchao
 * @date 2019/2/21
 */
@RestController
public class TestController {
    /**
     * 测试接口
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json;charset=UTF-8", value = "/")
    public String index() {
        return "success";
    }
}
