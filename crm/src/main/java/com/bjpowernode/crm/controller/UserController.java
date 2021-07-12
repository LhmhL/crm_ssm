package com.bjpowernode.crm.controller;

import com.bjpowernode.crm.domain.User;
import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.service.UserService;
import com.bjpowernode.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login.do" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(String loginAct, String loginPwd, HttpServletRequest request) throws LoginException {
        //将密码的明文形转换为MD5的密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        //接收浏览器端的ip地址
        String ip = request.getRemoteAddr();

        User user = userService.queryUser(loginAct, loginPwd, ip);
        request.getSession().setAttribute("user",user);
        Map<String,Object> map = new HashMap<>();
        map.put("success",true);
        return map;

//        try{
//            //业务层开发，统一使用代理类形态的接口对象
//            User user = userService.queryUser(loginAct, loginPwd, ip);
//            request.getSession().setAttribute("user",user);
//            //如果执行到此处，表示登录成功    将Map解析为Json返回到jsp页面
//            //将Map解析为Json返回到jsp页面
//            Map<String,Object> map = new HashMap<>();
//            map.put("success",true);
//            return map;
//        }catch (Exception e){
//            e.printStackTrace();
//            //一旦程序执行了catch块的信息，说明业务层为我们验证登录失败，抛出了异常
//            String msg = e.getMessage();
//            Map<String,Object> map = new HashMap<>();
//            map.put("success",false);
//            map.put("msg",msg);
//            return map;
//        }
    }
}
