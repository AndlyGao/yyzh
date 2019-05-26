package com.qc.yyzh.qc.controller;

import com.qc.yyzh.qc.entity.Up;
import com.qc.yyzh.qc.service.UpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qc
 * @date 2019/4/28
 */
@RestController
public class UpController {

    @Autowired
    private UpService upService;
    @GetMapping("/up/add")
    public int add(@RequestParam("text")String text){
        Up up=new Up();
        up.setText(text);
        try {
            upService.add(up);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
}
