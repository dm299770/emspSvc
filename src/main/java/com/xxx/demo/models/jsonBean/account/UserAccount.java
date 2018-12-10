package com.xxx.demo.models.jsonBean.account;

import java.util.Date;

/**
 * 用户余额
 */
public class UserAccount implements java.io.Serializable{

    private String userAccount; //用户关联ID
    private String balance;     //余额
    private Date createTime;    //创建时间
    private Date updateTime;    //修改时间


    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
