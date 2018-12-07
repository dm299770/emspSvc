package com.xxx.demo.services.account;

import com.alibaba.fastjson.JSONObject;

/**
 * @description:账户相关
 * @author:@leo.
 */
public interface AccountService {
    /**
     * 用户充电扣款
     * @param user_id 用户主键
     * @param money 充电扣费
     * @return 返回登录结果及用户信息
     */
    JSONObject deduct(String user_id, String money);

}
