package com.test.jwt;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		String userName = req.getParameter("username");
		String md5Pwd = req.getParameter("password");
		System.out.println("[GET] in login(..), from Params, userName=" + userName + ", md5Pwd=" + md5Pwd);

		User u = userRepository.verifyUser(userName, md5Pwd);
		if (u != null) { // query DB and verify user
			Date lastLoginTime = new Date();
			String jwtStr = JwtUtil.generateToken(userName, u.getRole(), lastLoginTime);
			return "{\"result\":\"ok\", \"userName\":\"" + userName + "\", \"jwt\":\"" + jwtStr + "\"}";
		} else {
			return "{\"result\":\"fail\", \"userName\":\"" + userName + "\", \"jwt\":" + "\"\"}";
		}
	}

	//---------------------------------------------------------------------------------------------------------

//	http://localhost:9090/user/login2
//	POST json data:
//	{
//		"userName": "User1",
//		"password": "MD5PWD1"
//	}

	@PostMapping("/login2")
	// @Transactional
	public String login2(HttpServletRequest req, HttpServletResponse resp, @RequestBody User user) {
		String userName = user.getUserName();
		String md5Pwd = user.getPassword();
		System.out.println("[POST] in login2(..), from RequesBody, userName=" + userName + ", md5Pwd=" + md5Pwd);

		User u = userRepository.verifyUser(userName, md5Pwd);
		if (u != null) { // query DB and verify user
			Date lastLoginTime = new Date();
			String jwtStr = JwtUtil.generateToken(userName, u.getRole(), lastLoginTime);
			return "{\"result\":\"ok\", \"userName\":\"" + userName + "\", \"jwt\":\"" + jwtStr + "\"}";
		} else {
			return "{\"result\":\"fail\", \"userName\":\"" + userName + "\", \"jwt\":" + "\"\"}";
		}
	}

}