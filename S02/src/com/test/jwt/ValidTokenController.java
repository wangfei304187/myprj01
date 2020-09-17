package com.test.jwt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class ValidTokenController {

    // http://localhost:9090/valid?token=eyJhbGci...

    @RequestMapping("/valid")
    public Map login(HttpServletRequest request) {
        String token = request.getParameter("token");
        return JwtUtil.validateToken(token);
    }
}