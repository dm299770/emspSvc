package com.xxx.demo.services.condition;

import com.alibaba.fastjson.JSONObject;

/**
 * @description:车辆状态相关信息
 * @author:@leo.
 */
public interface ConditionService {

    /**
     * 查询里程/电量
     *
     * @param user_id 用户主键
     * @param vin 车辆

     * @return 返回登录结果及用户信息
     */
    JSONObject carstatus(String user_id, String vin);
}
