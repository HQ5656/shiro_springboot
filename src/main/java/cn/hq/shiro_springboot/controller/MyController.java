package cn.hq.shiro_springboot.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class MyController {
    
    @RequestMapping({"/","/index"})
    public  String toIndex(Model model){
        model.addAttribute("msg","hello,shiro");
        return  "index";
    }
    @RequestMapping("/user/add")
    public  String add(){
        return "user/add";
    }
    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }
    @RequestMapping("/user/login")
    public  String toLogin(){
        return "user/login";
    }
    @RequestMapping("/login")
    public  String login(String username, String password, Model model, HttpServletRequest request){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
        //存一个session 去service测试 和shiro 是否一样
       /* HttpSession session = request.getSession();
        session.setAttribute("test","session");*/
        try {
            subject.login(usernamePasswordToken);
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不存在");
            return  "user/login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return  "user/login";
        }
    }
    @RequestMapping("/unauth")
    @ResponseBody
    public String unauth(){
        return "未经授权的页面";
    }
}
