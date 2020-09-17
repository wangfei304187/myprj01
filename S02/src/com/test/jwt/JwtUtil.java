package com.test.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
	
//    private static UserRepository userRepository;
//
//    @Autowired
//    public JwtUtil(UserRepository userRepository) {
//        JwtUtil.userRepository = userRepository;
//    }

    public static final long EXPIRATION_TIME = 120_000L; // 2 min
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    public static String generateToken(String userName, String role, Date generateTime) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("role", role);
        map.put("generateTime", generateTime);
        String jwt = Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(generateTime.getTime() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        System.out.println("generateToken: " + jwt);
        return jwt;
    }

    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> resp = new HashMap<String, Object>();
        if (token != null) {
            try {
                Map<String, Object> body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String userName = (String) (body.get("userName"));
                String role = (String) (body.get("role"));
                Date generateTime = new Date((Long) body.get("generateTime"));
                System.out.println("validateToken, userName=" + userName + ", role=" + role + ", generateTime=" + generateTime.getTime());
                
                if (userName == null || userName.isEmpty()) {
                    resp.put("ERR_MSG", "ERR_MSG_USERNAME_EMPTY");
                    return resp;
                }
//                resp.put("username", userName);
//                resp.put("generateTime", generateTime);
                if ("Admin".equals(role))
                {
                	resp.put("ERR_MSG", "ERR_MSG_OK");
                }
                else
                {
                	resp.put("ERR_MSG", "ERR_MSG_ROLE_INVALID");
                }
                return resp;
            } catch (SignatureException | MalformedJwtException e) {
                // TODO: handle exception
                // don't trust the JWT!
                // jwt 解析错误
                resp.put("ERR_MSG", "ERR_MSG_TOKEN_ERR");
                return resp;
            } catch (ExpiredJwtException e) {
                // TODO: handle exception
                // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，
                // 如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
                resp.put("ERR_MSG", "ERR_MSG_TOKEN_EXP");
                return resp;
            }
        } else {
            resp.put("ERR_MSG", "ERR_MSG_TOKEN_EMPTY");
            return resp;
        }
    }
    
//    public static Map<String, Object> validateToken(String token) {
//        Map<String, Object> resp = new HashMap<String, Object>();
//        if (token != null) {
//            // 解析token
//            try {
//                Map<String, Object> body = Jwts.parser()
//                        .setSigningKey(SECRET)
//                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                        .getBody();
//                String userName = (String) (body.get("userName"));
//                String role = (String) (body.get("role"));
//                Date generateTime = new Date((Long) body.get("generateTime"));
//                if (userName == null || userName.isEmpty()) {
//                    resp.put("ERR_MSG", "ERR_MSG_USERNAME_EMPTY");
//                    return resp;
//                }
//                //账号在别处登录
//                User tmpU = userRepository.findByUserName(userName);
//                if (tmpU.getLastLoginTime() != null && tmpU.getLastLoginTime().after(generateTime)) {
//                    resp.put("ERR_MSG", "ERR_MSG_LOGIN_DOU");
//                    return resp;
//                }
//                resp.put("username", userName);
//                resp.put("generateTime", generateTime);
//                return resp;
//            } catch (SignatureException | MalformedJwtException e) {
//                // TODO: handle exception
//                // don't trust the JWT!
//                // jwt 解析错误
//                resp.put("ERR_MSG", "ERR_MSG_TOKEN_ERR");
//                return resp;
//            } catch (ExpiredJwtException e) {
//                // TODO: handle exception
//                // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，
//                // 如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
//                resp.put("ERR_MSG", "ERR_MSG_TOKEN_EXP");
//                return resp;
//            }
//        } else {
//            resp.put("ERR_MSG", "ERR_MSG_TOKEN_EMPTY");
//            return resp;
//        }
//    }
}