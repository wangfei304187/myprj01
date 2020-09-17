package com.my.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.my.exception.StudyNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@RestController
@CrossOrigin(origins = "http://localhost:9090")
@RequestMapping("/api/study")
public class StudyController {

	private static Map<String, Study> studyRepo = new HashMap<>();
	
	static {
		Study s1 = new Study();
		s1.setBmStudyId("1");
		s1.setPatientName("Jack");
		s1.setAge(31);
		s1.setStudyDescription("this is study description 1");
		
		Study s2 = new Study();
		s2.setBmStudyId("2");
		s2.setPatientName("James");
		s2.setAge(32);
		s2.setStudyDescription("this is study description 2");
		
		studyRepo.put(s1.getBmStudyId(), s1);
		studyRepo.put(s2.getBmStudyId(), s2);
	}
	
	// http://localhost:8080/api/study
	@GetMapping("")
	public ResponseEntity<Object> getStudy()
	{
		return new ResponseEntity<>(studyRepo.values(), HttpStatus.OK);
	}
	
	// http://localhost:8080/api/study/2
	@GetMapping(value = "/{bmStudyId}")
	public ResponseEntity<Object> getStudyById(@PathVariable String bmStudyId)
	{
		return new ResponseEntity<>(studyRepo.get(bmStudyId), HttpStatus.OK);
	}
	
	
//	http://localhost:8080/api/study/create
//	{
//	    "bmStudyId": "3",
//	    "patientName": "Lili",
//	    "age": 23,
//	    "studyDescription": "this is study description 3"
//	}
	@PostMapping(value = "/create")
	public ResponseEntity<Object> createStudy(@RequestBody Study study)
	{
		studyRepo.put(study.getBmStudyId(), study);
		return new ResponseEntity<>("Study is created successfully", HttpStatus.CREATED);
	}
	
	
//	http://localhost:8080/api/study/2
//	{
//        "bmStudyId": "2",
//        "patientName": "James",
//        "age": 35,
//        "studyDescription": "this is study description 2 -- updated"
//    }
	@PutMapping(value = "/{bmStudyId}")
	public ResponseEntity<Object> updateStudy(@PathVariable String bmStudyId, @RequestBody Study study)
	{
		if (!studyRepo.containsKey(bmStudyId))
		{
//			http://localhost:8080/api/study/11
//			{
//		        "bmStudyId": "11",
//		        "patientName": "James",
//		        "age": 35,
//		        "studyDescription": "this is study description 2 -- updated"
//		    }
			
			throw new StudyNotFoundException();
		}
		studyRepo.remove(bmStudyId);
		study.setBmStudyId(bmStudyId);
		studyRepo.put(bmStudyId, study);
		return new ResponseEntity<>("Study is updated successfully", HttpStatus.OK);
	}
	
	// http://localhost:8080/api/study/2
	@RequestMapping(value = "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteStudy(@PathVariable("id") String bmStudyId)
	{
		studyRepo.remove(bmStudyId);
		return new ResponseEntity<>("Study is deleted successfully", HttpStatus.OK);
	}
	
	
	
	@GetMapping("/index")
	public String index() {
		return "Hello StudyController";
	}
	
	@Value("${spring.application.name}")
	private String name;
	
	@GetMapping("/name")
	public String name() {
		return name;
	}
	
	//=========================================
	// Need Authorization --- JWT
	
	// http://localhost:8080/api/study/auth
	@PostMapping("/auth")
	public ResponseEntity<Object> getStudyAuth(HttpServletRequest req, HttpServletResponse resp)
	{
		String token = req.getHeader("Authorization");
		
		System.out.println("[POST] StudyController::getStudyAuth, token=" + token);
		
		Map<String, Object> result = validateToken(token);
		System.out.println("result: " + result);
		if (!result.get("ERR_MSG").equals("ERR_MSG_OK"))
		{
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(studyRepo.values(), HttpStatus.OK);
	}
	
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    
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
//	                resp.put("username", userName);
//	                resp.put("generateTime", generateTime);
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
}
