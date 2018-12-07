package com.xxx.demo.repository.redistemplate.impl;

import com.xxx.demo.repository.redistemplate.IUserDao;
import com.xxx.demo.repository.redistemplate.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private RedisRepository redisRepository;


    @Override
    public void addDeviceNo(String phoneNum, String deiviceNo) {
        redisRepository.set(phoneNum,deiviceNo);
    }

    @Override
    public void updateDeviceNo(String phoneNum, String deiviceNo) {
        redisRepository.remove(phoneNum);
        redisRepository.set(phoneNum , deiviceNo);
    }
}
