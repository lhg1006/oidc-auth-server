package com.example.oidcauth.service;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOidcUserInfoService {

    public OidcUserInfo loadUser(String username) {
        Map<String, Object> claims = new HashMap<>();

        // 실제 프로젝트에서는 데이터베이스에서 사용자 정보를 조회합니다
        if ("user".equals(username)) {
            return OidcUserInfo.builder()
                    .subject(username)
                    .name("Test User")
                    .givenName("Test")
                    .familyName("User")
                    .email(username + "@example.com")
                    .emailVerified(true)
                    .preferredUsername(username)
                    .profile("https://example.com/profile/" + username)
                    .picture("https://example.com/avatar/" + username)
                    .phoneNumber("+1234567890")
                    .phoneNumberVerified(true)
                    .claim("department", "Engineering")
                    .claim("employee_id", "12345")
                    .build();
        }

        // 기본 사용자 정보
        return OidcUserInfo.builder()
                .subject(username)
                .name(username)
                .email(username + "@example.com")
                .build();
    }
}