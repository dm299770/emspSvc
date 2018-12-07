package com.xxx.demo.controller.condition;

import com.alibaba.fastjson.JSONObject;
import com.xxx.demo.dto.sys.UserInfo;
import com.xxx.demo.frame.annotation.CurrentUser;
import com.xxx.demo.frame.annotation.LoginRequired;
import com.xxx.demo.services.condition.ConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:登录
 * @author:@leo.
 * */
@Controller
//@RequestMapping("condition")
public class ConditionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConditionService conditionService;


    /**
     * 获取用户里程以及电量
     * */

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/carstatus")
    public Object carStatus(@CurrentUser UserInfo user) {
        String user_id = user.getUserId();
        JSONObject result = conditionService.carstatus(user_id,""/**vin号*/);
        return result;
    }





}
