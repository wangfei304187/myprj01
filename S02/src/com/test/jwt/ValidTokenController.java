package com.test.jwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ValidTokenController {

    // http://localhost:9090/valid?token=eyJhbGciOiJIUzUxMiJ9.eyJnZW5lcmF0ZVRpbWUiOjE2MDAyMzcxOTk2OTQsImV4cCI6MTYwMDI0MDc5OSwidXNlcm5hbWUiOiJVc2VyMSJ9.CkTIuNAmQZCp0UKT8_Y9XJxeFAAMoUPGni1UIMrpR1XaxM7DsdATbTSS7oRrmkWej6U62gobpAMpBifJ5Iurkg

    @RequestMapping("/valid")
    public Map login(HttpServletRequest request) {
        String token = request.getParameter("token");
        return JwtUtil.validateToken(token);
    }
}