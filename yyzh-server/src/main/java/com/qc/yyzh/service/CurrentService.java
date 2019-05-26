package com.qc.yyzh.service;

import com.qc.yyzh.mapper.CurrentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qc
 * @date 2019/4/26
 */
@Service
public class CurrentService {
    @Autowired
    CurrentMapper currentMapper;

    public   void login(){
        currentMapper.login();
    }

    public void logout() {
        currentMapper.logout();
    }
}
