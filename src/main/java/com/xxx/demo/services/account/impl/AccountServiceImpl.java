package com.xxx.demo.services.account.impl;

import com.alibaba.fastjson.JSONObject;
import com.xxx.demo.dto.sys.UserInfo;
import com.xxx.demo.frame.constants.APIResultConstants;
import com.xxx.demo.mapper.account.AccountMapper;
import com.xxx.demo.mapper.user.TsUserMapper;
import com.xxx.demo.models.jsonBean.account.TtChargeFlow;
import com.xxx.demo.models.jsonBean.account.UserAccount;
import com.xxx.demo.models.jsonBean.user.UserInfoData;
import com.xxx.demo.services.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private static final String BALANCE = "balance";
    private static final String DEDUCT_SUCCESS = "已缴费成功";
    private static final String DEDUCT_FATL = "余额不足,缴费失败";
    private static final String DEDUCT_ERROR = "服务异常,请稍后重试或联系相关app";
    private static final String DEDUCT_FORMATERROR = "扣款金额格式不正确,请检查";
    private static final String CHARGETO="PowerShare";//到达方

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TsUserMapper tsUserMapper;

    /**
     *扣款计费
     *
     * */
    @Override
    //@Transactional
    public JSONObject deduct(String user_id, String money) {
        JSONObject json = new JSONObject();
        UserAccount userAccount = null;
        UserInfo userInfo=null;
        if (money==null && money.isEmpty()){
            json.put(APIResultConstants.STATUS,APIResultConstants.ERROR_STATUS);
            json.put(APIResultConstants.MSG,DEDUCT_ERROR);
        }
        try {
            userAccount= accountMapper.selectBalance(user_id);
            Double balance= Double.valueOf(userAccount.getBalance());//用户余额
            Double moneyD = Double.parseDouble(money);  //消费金额
            Double amount=balance-moneyD;
            Date updateTime=new Date();    //下单时间及修改时间

            //扣费操作
            if(moneyD<balance) {
                accountMapper.upadteBalance(user_id,String.valueOf(amount),updateTime);
                json.put(APIResultConstants.STATUS, APIResultConstants.SUCCESS_STATUS);
                json.put(APIResultConstants.MSG, DEDUCT_SUCCESS);
                json.put(BALANCE, userAccount.getBalance());
            }else if(balance.equals(moneyD)){ //如果余额和消费金额相等，则修改余额为零
                accountMapper.upadteBalance(user_id,"0",updateTime);
                json.put(APIResultConstants.STATUS, APIResultConstants.SUCCESS_STATUS);
                json.put(APIResultConstants.MSG, DEDUCT_SUCCESS);
                json.put(BALANCE, userAccount.getBalance());
            }else{
                //余额不足
                json.put(APIResultConstants.STATUS,APIResultConstants.FAIL_STATUS);
                json.put(APIResultConstants.MSG,DEDUCT_FATL);
                json.put(BALANCE,userAccount.getBalance());
            }
            //扣费流水记录
            if(amount>=0) {
                String id = UUID.randomUUID().toString();//流水单号，
                Integer direction = -1; //增减标识（扣款为减少，-1）
                //发起方查询
                userInfo=tsUserMapper.findUserPhoneNum(user_id);
                accountMapper.saveChargeFlow(id,user_id,moneyD,direction,updateTime,userInfo.getPhoneNum(),CHARGETO);
                json.put(APIResultConstants.STATUS,APIResultConstants.SUCCESS_STATUS);
                json.put(APIResultConstants.MSG,DEDUCT_SUCCESS);
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
