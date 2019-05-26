package com.qc.yyzh.controller;

import com.qc.yyzh.entity.Music;
import com.qc.yyzh.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qc
 * @date 2019/4/26
 */
@RestController
public class MusicController {

    @Autowired
    MusicService musicService;

    Map<String,Object> map;
    @GetMapping("/querymusicbytype")
    public Map<String,Object> querybyType(@RequestParam("type") int type){
        map=new HashMap<>();
        List<Music> querybytype = musicService.querybytype(type);

        map.put("music",querybytype);
        return map;
    }
    @GetMapping("/querymusicbyname")
    public Map<String,Object> querybyType(@RequestParam("name") String  name){
        map=new HashMap<>();
        List<Music> querybytype = musicService.querybyname(name);
        map.put("music",querybytype);
        return map;
    }

}
