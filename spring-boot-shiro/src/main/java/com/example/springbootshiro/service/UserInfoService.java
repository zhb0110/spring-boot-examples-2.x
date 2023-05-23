package com.example.springbootshiro.service;

import com.example.springbootshiro.model.UserInfo;

public interface UserInfoService {
    /**
     * 通过username查找用户信息;
     */
    public UserInfo findByUsername(String username);
}
