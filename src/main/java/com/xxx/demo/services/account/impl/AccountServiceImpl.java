package com.xxx.demo.services.account.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxx.demo.frame.constants.APIResultConstants;
import com.xxx.demo.services.account.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String BALANCE = "balance";
    private static final String DEDUCT_SUCCESS = "已缴费成功";
    private static final String DEDUCT_FATL = "余额不足,缴费失败";
    private static final String DEDUCT_ERROR = "服务异常,请稍后重试或联系相关app";
    private static final String DEDUCT_FORMATERROR = "扣款金额格式不正确,请检查";

    /**
     *扣款计费
     * */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public JSONObject deduct(String user_id, String money) {
        JSONObject json = new JSONObject();
        try {

            Double moneyD = Double.parseDouble(money);
            //扣费操作

            //扣费流水记录

            //计算余额
            Double balance = 123.45;
            if(moneyD>balance){
                //余额不足
                json.put(APIResultConstants.STATUS,APIResultConstants.SUCCESS_STATUS);
                json.put(APIResultConstants.MSG,DEDUCT_SUCCESS);
                json.put(BALANCE,balance);

            }else{
                json.put(APIResultConstants.STATUS,APIResultConstants.FAIL_STATUS);
                json.put(APIResultConstants.MSG,DEDUCT_FATL);
                json.put(BALANCE,balance);
            }

        } catch (NumberFormatException e) {
            json.put(APIResultConstants.STATUS,APIResultConstants.ERROR_STATUS);
            json.put(APIResultConstants.MSG,DEDUCT_FORMATERROR);
            e.printStackTrace();
        }catch (Exception e) {
            json.put(APIResultConstants.STATUS,APIResultConstants.ERROR_STATUS);
            json.put(APIResultConstants.MSG,DEDUCT_ERROR);
            e.printStackTrace();
        }


        return json;
    }
}
