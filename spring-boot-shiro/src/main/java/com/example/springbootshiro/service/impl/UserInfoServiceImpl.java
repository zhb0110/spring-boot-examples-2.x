package com.example.springbootshiro.service.impl;

import com.example.springbootshiro.dao.UserInfoDao;
import com.example.springbootshiro.model.UserInfo;
import com.example.springbootshiro.service.UserInfoService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}
