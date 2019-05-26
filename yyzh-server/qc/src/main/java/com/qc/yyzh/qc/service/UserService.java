package com.qc.yyzh.qc.service;


import com.qc.yyzh.qc.entity.User;
import com.qc.yyzh.qc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qc
 * @date 2019/4/26
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User queryUserforName(String name) {
        return userMapper.login(name);
    }

    public void addUser(User user) {
        userMapper.register(user);
    }
}
