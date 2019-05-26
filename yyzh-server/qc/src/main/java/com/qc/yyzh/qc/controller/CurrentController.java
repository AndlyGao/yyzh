package com.qc.yyzh.qc.controller;


import com.qc.yyzh.qc.service.CurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qc
 * @date 2019/4/26
 */
@RestController
public class CurrentController {
    @Autowired
    CurrentService currentService;
    @GetMapping("/lll")
    public  int login(){
        try {
            currentService.login();
        }catch (Exception e){
            return 0;
        }

        return 1;
    }
    @GetMapping("/llll")
    public  int logout(){
        try {
            currentService.logout();
        }catch (Exception e){
            return 0;
        }

        return 1;
    }
    @GetMapping("/lllll")
    public  int getcurrentuser(){
        try {
           return  currentService.getCurrent();
        }catch (Exception e){
            return 0;
        }
    }
}
