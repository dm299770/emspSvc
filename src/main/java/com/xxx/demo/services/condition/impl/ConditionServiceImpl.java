package com.xxx.demo.services.condition.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxx.demo.frame.constants.APIResultConstants;
import com.xxx.demo.frame.constants.AppResultConstants;
import com.xxx.demo.services.condition.ConditionService;
import org.springframework.stereotype.Service;

/**
 * @description:车辆状态查询
 * @author:@leo.
 */
@Service
public class ConditionServiceImpl implements ConditionService {
    @Override
    public JSONObject carstatus(String user_id, String vin) {
        JSONObject result = new JSONObject();

        //验证参数?

        result.put(APIResultConstants.SOC, 0.8);//soc 暂定 0.8
        result.put(APIResultConstants.RANG,12.3);//rang 暂定


        return result;
    }
}
