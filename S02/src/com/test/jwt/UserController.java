package com.test.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	
	// http://localhost:9090/user/login?username=User1&password=MD5PWD1
	// {"result":"ok", "userName":"User1", "jwt":"eyJhbGciOiJIUzUxMiJ9.eyJnZW5lcmF0ZVRpbWUiOjE2MDAxODIzMTE3OTQsImV4cCI6MTYwMDE4NTkxMSwidXNlcm5hbWUiOiJVc2VyMSJ9.O26bAa0UOnO3J9u0fbuPXXnlVFic_J2kLDanuiylhkJmQTtqBlIE76mRQh_tp-UomgywaTwioZhaJbD8ssZTkg"}
	
	// http://localhost:9090/user/login?username=User1&password=MD5PWD2
	// {"result":"fail", "userName":User1, "jwt":""}
	
	// 注册或登录
	@GetMapping("/login")
	// @Transactional
//    public UserResponse login(User user) {
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("username");
		String md5Pwd = req.getParameter("password");
		System.out.println("in login(..), userName=" + userName + ", md5Pwd=" + md5Pwd);

		if (userRepository.isValid(userName, md5Pwd)) {
			Date lastLoginTime = new Date();
			String jwtStr = JwtUtil.generateToken(userName, lastLoginTime);
			return "{\"result\":\"ok\", \"userName\":\"" + userName + "\", \"jwt\":\"" + jwtStr + "\"}";
		} else {
			return "{\"result\":\"fail\", \"userName\":\"" + userName + "\", \"jwt\":" + "\"\"}";
		}
	}


//    //注册或登录
//    @GetMapping("/login")
//    // @Transactional
////    public UserResponse login(User user) {
//    public UserResponse login(HttpServletRequest req, HttpServletResponse resp) {
////        String userName = user.getUserName();
////        String password = user.getPassword();
//    	String userName = req.getParameter("username");
//         String password = req.getParameter("password");
//        System.out.println("in login(..), userName=" + userName + ", password=" + password);
//        //TODO  检验参数的完整性
//        UserResponse userResponse = new UserResponse();
//        User tUser = userRepository.findByUserName(userName);
//        //检验username是否存在
//        if (tUser != null) {
//        	 tUser.setLastLoginTime(new Date());
//            //检验密码是否正确
//            if (!tUser.getPassword().equals(password)) {
//                userResponse.setErrNum(UserRepository.ERR_NUM_PWD_ERR);
//                userResponse.setErrMsg("ERR_MSG_PWD_ERR");
//                return userResponse;
//            }
//
//            userRepository.update(tUser);
//            
//            userResponse.setErrNum(UserRepository.ERR_NUM_OK);
//            userResponse.setErrMsg("ERR_MSG_OK");
//            userResponse.setUserId(tUser.getUserId());
//            userResponse.setUserName(tUser.getUserName());
//            userResponse.setToken(JwtUtil.generateToken(tUser.getUserName(), tUser.getLastLoginTime()));
//            return userResponse;
//        }
//        else {
////            try {
////                userRepository.save(tUser);
////            } catch (Exception e) {
////                userResponse.setErrNum(UserRepository.ERR_NUM_SERVER_ERR);
////                userResponse.setErrMsg("ERR_MSG_SERVER_ERR");
////                return userResponse;
////            }
//        	 userResponse.setErrNum(UserRepository.ERR_NUM_SERVER_ERR);
//             userResponse.setErrMsg("ERR_MSG_SERVER_ERR");
//             return userResponse;
//        }
////        userResponse.setErrNum(UserRepository.ERR_NUM_OK);
////        userResponse.setErrMsg("ERR_MSG_OK");
////        userResponse.setUserId(tUser.getUserId());
////        userResponse.setUserName(tUser.getUserName());
////        userResponse.setToken(JwtUtil.generateToken(tUser.getUserName(), tUser.getLastLoginTime()));
////        return userResponse;
//    }
}