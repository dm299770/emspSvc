package com.xxx.demo.mapper.account;

import com.xxx.demo.models.jsonBean.account.UserAccount;
import org.apache.ibatis.annotations.Param;
import java.util.Date;

public interface AccountMapper {

    UserAccount selectBalance(@Param("userAccount") String user_id);

    void upadteBalance(@Param("userAccount")String userAccount ,@Param("balance") String  balance,@Param("updateTime")Date updateTime);

    void saveChargeFlow(@Param("id") String id, @Param("userAccount") String userAccount,
                        @Param("amount") Double moneyD, @Param("direction") Integer direction,
                        @Param("chargeDate") Date updateTime, @Param("chargeFrom") String phoneNum, @Param("chargeTo") String chargeTo);
}
