package com.my.app.controller;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.app.pojo.UserAccount;

@RestController
public class LoginController {

	@RequiresRoles(value="{Admin, Operator}")
    @GetMapping("/login")
    public String login(UserAccount user) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("Admin");
//            subject.checkPermissions("query", "add");
        } catch (UnknownAccountException e) {
            System.out.println(e.getMessage());
            return "用户名不存在！";
        } catch (AuthenticationException e) {
        	System.out.println(e.getMessage());
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
        	System.out.println(e.getMessage());
            return "没有权限";
        }
        return "login success";
    }

    @RequiresRoles("Admin")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("read")
    @GetMapping("/index")
    public String index() {
        return "index success!";
    }

    @RequiresPermissions("write")
    @GetMapping("/add")
    public String add() {
        return "add success!";
    }
}