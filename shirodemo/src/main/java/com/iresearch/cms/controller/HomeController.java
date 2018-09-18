package com.iresearch.cms.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:曹洋
 * @Description：
 * @Date: Create in  2018/9/15 001515:22
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String index(){
        return"login";
    }

    @RequestMapping(value="/submitLogin",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitLogin(String username,String password){
        System.out.println(username+"-----"+password);
        String msg = "";
        Map<String, Object> map = new HashMap<>();
        try {
            UsernamePasswordToken token = token = new UsernamePasswordToken(username, password);
            char[] password1 = token.getPassword();
            String s = password1.toString();

            System.out.println("*******"+s);
            SecurityUtils.getSubject().login(token);
            token.setRememberMe(true);
            map.put("status",200);
        } catch (UnknownAccountException e) {
            msg = "UnknownAccountException -- > 账号不存在：";
            map.put("status",400);
        } catch (IncorrectCredentialsException e){
            msg = "IncorrectCredentialsException -- > 密码不正确：";
            map.put("status",500);
        }catch (Exception exception){
            msg = "else >> "+exception;
            System.out.println("else -- >" + exception);
        }
        return map;
    }

    /**
     * 登录成功跳转路径
     * @return
     */
    @RequestMapping(value = "/index")
    public String loginSucess(){
        return "index";
    }


    /**
     * 退出功能
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        SecurityUtils.getSubject().logout();
        return "login";
    }

    @RequestMapping("/403")
    public String error(){
        return "403";
    }

}
