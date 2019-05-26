package com.qc.yyzh.qc.service;

import com.qc.yyzh.qc.entity.Up;
import com.qc.yyzh.qc.entity.User;
import com.qc.yyzh.qc.mapper.UpMapper;
import com.qc.yyzh.qc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpService {
    @Autowired
    private UpMapper upMapper;

    public void add(Up up) {

        upMapper.insert(up);
    }
}
