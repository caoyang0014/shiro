package com.iresearch.cms.controller;

import com.iresearch.cms.entity.SysUser;
import com.iresearch.cms.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:曹洋
 * @Description：
 * @Date: Create in  2018/9/17 001710:29
 */
@Controller
@RequestMapping("/userInfo")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * 查询功能
     * @return
     */
    @RequestMapping(value = "/userList")
    @RequiresPermissions("userInfo:view")//权限管理
    public String userInfo(){
        return "userInfo";
    }


    /**
     * 新增功能
     * @return
     */
    @RequestMapping(value = "/userAdd")
    @RequiresPermissions(value = "userInfo:add")
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    /**
     * 删除功能
     * @return
     */
    @RequestMapping(value = "/userDel")
    @RequiresPermissions(value = "userInfo:del")
    public String userInfoDel(){
        return "userInfoDel";
    }

    @RequestMapping("/add")
    public void add(SysUser user){
        user.setState((byte)0);
        //user.setSalt("salt");
        String hashAlgorithmName = "MD5";
        ByteSource bytes = ByteSource.Util.bytes(user.getCredentialsSalt());
        System.out.print(bytes);
        int hashIterations = 2;

        SimpleHash hash = new SimpleHash(hashAlgorithmName,user.getUsername(),bytes,hashIterations);
        String s = hash.toString();
        user.setSalt(s);
        SimpleHash simpleHash = new SimpleHash(hashAlgorithmName, bytes, hashAlgorithmName);
        String s1 = simpleHash.toString();
        user.setPassword(s1);

        userInfoService.add(user);
    }
}
