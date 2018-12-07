package com.xxx.demo.controller.account;

import com.alibaba.fastjson.JSONObject;
import com.xxx.demo.dto.sys.UserInfo;
import com.xxx.demo.frame.annotation.CurrentUser;
import com.xxx.demo.frame.annotation.LoginRequired;
import com.xxx.demo.frame.constants.APIResultConstants;
import com.xxx.demo.models.jsonBean.account.DeductRequestBody;
import com.xxx.demo.services.account.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description:账户
 * @author:@leo.
 */
@Controller
//@RequestMapping("/account")
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;

    /**
     * 用户充电扣款
     * */

    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "/deduct")
    public Object deduct(@CurrentUser UserInfo user,@RequestBody DeductRequestBody money) {

        String user_id = user.getUserId();
        JSONObject jsonObject = accountService.deduct(user_id,money.getMoney());
        return jsonObject;
    }

}
