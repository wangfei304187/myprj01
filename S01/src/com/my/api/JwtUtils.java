package com.my.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

public class JwtUtils {

	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Bearer";

	public static Map<String, Object> validateToken(String token) {
		Map<String, Object> resp = new HashMap<String, Object>();
		if (token != null) {
			try {
				Map<String, Object> body = Jwts.parser().setSigningKey(SECRET)
				        .parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
				String userName = (String) (body.get("userName"));
				String role = (String) (body.get("role"));
				Date generateTime = new Date((Long) body.get("generateTime"));
				System.out.println("validateToken, userName=" + userName + ", role=" + role + ", generateTime="
				        + generateTime.getTime());

				if (userName == null || userName.isEmpty()) {
					resp.put("ERR_MSG", "ERR_MSG_USERNAME_EMPTY");
					return resp;
				}
				// resp.put("username", userName);
				// resp.put("generateTime", generateTime);
				if ("Admin".equals(role)) {
					resp.put("ERR_MSG", "ERR_MSG_OK");
				} else {
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
}
