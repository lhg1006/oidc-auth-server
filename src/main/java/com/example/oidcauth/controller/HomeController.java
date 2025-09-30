package com.example.oidcauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "<!DOCTYPE html>" +
                    "<html><head><title>OIDC Server</title></head>" +
                    "<body style='font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px;'>" +
                    "<h1>OIDC 인증 서버</h1>" +
                    "<p>안녕하세요, <strong>" + authentication.getName() + "</strong>님!</p>" +
                    "<p>로그인이 완료되었습니다.</p>" +
                    "<h2>클라이언트 애플리케이션</h2>" +
                    "<p><a href='http://localhost:3003/api/auth/signin/oidc-server' style='display: inline-block; background: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>" +
                    "클라이언트 앱으로 이동 (포트 3003)</a></p>" +
                    "<p style='margin-top: 10px; font-size: 0.9em; color: #666;'>또는 <a href='http://localhost:3003' target='_blank'>기존 세션으로 이동</a></p>" +
                    "<hr>" +
                    "<p><a href='/logout' style='color: #dc3545;'>로그아웃</a></p>" +
                    "</body></html>";
        } else {
            return "<!DOCTYPE html>" +
                    "<html><head><title>OIDC Server</title></head>" +
                    "<body style='font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px;'>" +
                    "<h1>OIDC 인증 서버</h1>" +
                    "<p>인증이 필요합니다.</p>" +
                    "<p><a href='/login' style='display: inline-block; background: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>" +
                    "로그인</a></p>" +
                    "</body></html>";
        }
    }

    @GetMapping("/userinfo")
    @ResponseBody
    public Map<String, Object> userInfo(@AuthenticationPrincipal Jwt jwt) {
        Map<String, Object> userInfo = new HashMap<>();

        if (jwt != null) {
            userInfo.put("sub", jwt.getSubject());
            userInfo.put("name", jwt.getClaimAsString("name"));
            userInfo.put("email", jwt.getClaimAsString("email"));
            userInfo.put("email_verified", jwt.getClaimAsBoolean("email_verified"));
            userInfo.put("phone_number", jwt.getClaimAsString("phone_number"));
            userInfo.put("phone_verified", jwt.getClaimAsBoolean("phone_verified"));
            userInfo.put("department", jwt.getClaimAsString("department"));
            userInfo.put("employee_id", jwt.getClaimAsString("employee_id"));
            userInfo.put("role", jwt.getClaimAsString("role"));
        }

        return userInfo;
    }
}