package com.test.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //注册或登录
    @RequestMapping("/login")
    @Transactional
    public UserResponse login(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        //TODO  检验参数的完整性
        UserResponse userResponse = new UserResponse();
        User tUser = userRepository.findByUserName(username);
        //检验username是否存在
        user.setLastLoginTime(new Date());
        if (tUser != null) {
            //检验密码是否正确
            if (!tUser.getPassword().equals(password)) {
                userResponse.setErrNum(UserRepository.ERR_NUM_PWD_ERR);
                userResponse.setErrMsg("ERR_MSG_PWD_ERR");
                return userResponse;
            }

            userRepository.updateLastLoginTime(user);
        } else {
            try {
                tUser = userRepository.save(user);
            } catch (Exception e) {
                userResponse.setErrNum(UserRepository.ERR_NUM_SERVER_ERR);
                userResponse.setErrMsg("ERR_MSG_SERVER_ERR");
                return userResponse;
            }
        }
        userResponse.setErrNum(UserRepository.ERR_NUM_OK);
        userResponse.setErrMsg("ERR_MSG_OK");
        userResponse.setUserName(username);
        userResponse.setUserId(tUser.getUserId());
        userResponse.setToken(JwtUtil.generateToken(username, user.getLastLoginTime()));
        return userResponse;
    }
}