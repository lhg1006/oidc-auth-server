package com.example.oidcauth.config;

import com.example.oidcauth.entity.Student;
import com.example.oidcauth.entity.User;
import com.example.oidcauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class TokenCustomizerConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
        return context -> {
            Authentication principal = context.getPrincipal();

            try {
                // 데이터베이스에서 실제 사용자 또는 학생 정보 조회
                Object userOrStudent = userDetailsService.findUserByLoginId(principal.getName());

                if (context.getTokenType().getValue().equals("id_token")) {
                    // ID Token에 실제 사용자 정보 추가
                    context.getClaims().claims(claims -> {
                        if (userOrStudent instanceof Student) {
                            Student student = (Student) userOrStudent;

                            // 기본 OIDC 클레임
                            claims.put("sub", student.getLoginId());
                            claims.put("name", student.getStudentName());
                            claims.put("given_name", student.getStudentName());
                            claims.put("family_name", "");
                            claims.put("preferred_username", student.getLoginId());
                            claims.put("phone_number", student.getStudentPhone());
                            claims.put("email", student.getLoginId() + "@student.edu");
                            claims.put("email_verified", true);
                            claims.put("phone_number_verified", true);

                            // 학생 정보
                            claims.put("student_name", student.getStudentName());
                            claims.put("student_phone", student.getStudentPhone());
                            claims.put("grade", student.getGrade());
                            claims.put("school", student.getSchool());
                            claims.put("address", student.getAddress());
                            claims.put("account_type", student.getAccountType().toString());
                            claims.put("active", student.getActive());

                            // 학부모 정보
                            claims.put("parent_name", student.getParentName());
                            claims.put("parent_phone", student.getParentPhone());

                            // 학습 정보
                            claims.put("subject", student.getSubject().toString());
                            claims.put("monthly_classes", student.getMonthlyClasses());
                            claims.put("reading_score", student.getReadingScore());
                            claims.put("start_session", student.getStartSession());
                            claims.put("points", student.getPoints());

                            // 추가 정보
                            if (student.getRegistrationDate() != null) {
                                claims.put("registration_date", student.getRegistrationDate().toString());
                            }
                            claims.put("notes", student.getNotes());

                            // 지점 정보
                            if (student.getBranch() != null) {
                                claims.put("branch", student.getBranch().getBranchName());
                            }
                        } else if (userOrStudent instanceof User) {
                            User user = (User) userOrStudent;

                            // 기본 OIDC 클레임
                            claims.put("sub", user.getUsername());
                            claims.put("email", user.getEmail());
                            claims.put("name", user.getFullName());
                            claims.put("given_name", user.getFirstName());
                            claims.put("family_name", user.getLastName());
                            claims.put("preferred_username", user.getUsername());
                            claims.put("phone_number", user.getPhoneNumber());
                            claims.put("email_verified", user.getEmailVerified());
                            claims.put("phone_number_verified", user.getPhoneVerified());

                            // 직원 정보
                            claims.put("department", user.getDepartment());
                            claims.put("employee_id", user.getEmployeeId());
                            claims.put("role", user.getRole().toString());

                            // 지점 정보
                            if (user.getBranch() != null) {
                                claims.put("branch", user.getBranch().getBranchName());
                            }
                        }

                        // 역할 정보 추가
                        Set<String> roles = principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet());
                        claims.put("roles", roles);
                    });
                }

                if (context.getTokenType().getValue().equals("access_token")) {
                    // Access Token에도 기본 정보 추가
                    context.getClaims().claims(claims -> {
                        if (userOrStudent instanceof Student) {
                            Student student = (Student) userOrStudent;
                            claims.put("sub", student.getLoginId());
                            claims.put("name", student.getStudentName());
                        } else if (userOrStudent instanceof User) {
                            User user = (User) userOrStudent;
                            claims.put("sub", user.getUsername());
                            claims.put("email", user.getEmail());
                            claims.put("name", user.getFullName());
                            claims.put("department", user.getDepartment());
                        }

                        Set<String> authorities = principal.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toSet());
                        claims.put("authorities", authorities);
                    });
                }
            } catch (UsernameNotFoundException e) {
                // 사용자를 찾을 수 없는 경우 기본값 사용
                context.getClaims().claims(claims -> {
                    claims.put("sub", principal.getName());
                    claims.put("email", principal.getName() + "@example.com");
                    claims.put("name", principal.getName());
                });
            }
        };
    }
}