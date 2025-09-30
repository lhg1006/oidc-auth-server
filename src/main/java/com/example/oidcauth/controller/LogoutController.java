package com.example.oidcauth.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication,
            @RequestParam(value = "post_logout_redirect_uri", required = false) String redirectUri) {

        // 로그아웃 처리
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        // 세션 완전히 무효화
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // post_logout_redirect_uri가 제공되면 해당 URL로 리다이렉트
        if (redirectUri != null && !redirectUri.isEmpty()) {
            return "redirect:" + redirectUri;
        }

        // 클라이언트 앱의 로그아웃도 함께 처리하고 홈으로 리다이렉트
        return "redirect:http://localhost:3003/api/auth/signout?callbackUrl=http://localhost:3003/";
    }
}