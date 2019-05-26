package com.qc.yyzh.qc.service;


import com.qc.yyzh.qc.entity.Music;
import com.qc.yyzh.qc.mapper.MusicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qc
 * @date 2019/4/26
 */
@Service
public class MusicService {

    @Autowired
    private MusicMapper musicMapper;

    public List<Music> querybytype(int type) {
        return  musicMapper.querybyType(type);
    }
    public List<Music> querybyname(String  name) {
     return    musicMapper.querybyName(name);
    }
}
