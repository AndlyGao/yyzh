package com.qc.yyzh.qc.controller;


import com.qc.yyzh.qc.entity.User;
import com.qc.yyzh.qc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qc
 * @date 2019/4/26
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public int login(@RequestParam("name") String name, @RequestParam("password") String password){
        User user = userService.queryUserforName(name);
        try {
            if(password.equals(user.getUserPassword())){
                return 1111111111;

            }else{
                return 0;
            }
        }catch (Exception e){
            return 0;
        }

    }
    @GetMapping("/register")
    public int register(@RequestParam("name") String name, @RequestParam("password") String password
    ,@RequestParam("email") String email,@RequestParam("phone") String phone){
        User user2 = userService.queryUserforName(name);

        if(user2==null){
            try {
                User user =new User();
                user.setUserEmail(email);
                user.setUserName(name);
                user.setUserPassword(password);
                user.setUserPhone(phone);
                userService.addUser(user);
                return 1;

            }catch (Exception e){
                return 0;

            }
        }
        else{
            return 0;
        }


    }

}
