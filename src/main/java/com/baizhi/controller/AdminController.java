package com.baizhi.controller;

import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(Admin admin, String inputCode, HttpServletRequest request){
       /*Map<String, Object> map = new HashMap<>();
        try {
            adminService.login(admin,inputCode,request);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;*/
        String code = (String) request.getSession().getAttribute("code");
        Map<String, Object> map = new HashMap<>();
        if (code.equals(inputCode)) {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(),admin.getPassword());

            try {
                subject.login(token);
                map.put("status",true);
            } catch (UnknownAccountException e) {
                map.put("status",false);
                map.put("message","用户不存在");
            } catch (IncorrectCredentialsException e){
                map.put("status",false);
                map.put("message","密码错误");
            }
            return map;
        }else {
            map.put("status",false);
            map.put("message","验证码错误");
            return map;
        }
    }
    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }
}
