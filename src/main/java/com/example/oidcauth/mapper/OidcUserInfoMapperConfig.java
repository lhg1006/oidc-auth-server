package com.example.oidcauth.mapper;

import com.example.oidcauth.entity.Student;
import com.example.oidcauth.entity.User;
import com.example.oidcauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Configuration
public class OidcUserInfoMapperConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public Function<OidcUserInfoAuthenticationContext, OidcUserInfo> oidcUserInfoMapper() {
        return context -> {
            OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
            String loginId = authentication.getName();

            try {
                Object userEntity = userDetailsService.findUserByLoginId(loginId);

                if (userEntity instanceof Student) {
                    Student student = (Student) userEntity;

                    return OidcUserInfo.builder()
                            .subject(student.getLoginId())
                            .name(student.getStudentName())
                            .givenName(student.getStudentName().length() > 1 ?
                                student.getStudentName().substring(1) : student.getStudentName())
                            .familyName(student.getStudentName().length() > 0 ?
                                student.getStudentName().substring(0, 1) : "")
                            .preferredUsername(student.getLoginId())
                            .profile("https://education.co.kr/student/" + student.getLoginId())
                            .website("https://education.co.kr")
                            .email(student.getLoginId() + "@education.co.kr")
                            .emailVerified(true)
                            .phoneNumber(student.getStudentPhone())
                            .phoneNumberVerified(true)
                            .zoneinfo("Asia/Seoul")
                            .locale("ko-KR")
                            .updatedAt(student.getUpdatedAt() != null ?
                                student.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)
                            .claim("student_id", student.getId())
                            .claim("branch_id", student.getBranch() != null ? student.getBranch().getBranchId() : null)
                            .claim("branch_name", student.getBranch() != null ? student.getBranch().getBranchName() : null)
                            .claim("account_type", student.getAccountType().name())
                            .claim("subject", student.getSubject() != null ? student.getSubject().name() : null)
                            .claim("grade", student.getGrade())
                            .claim("school_name", student.getSchool())
                            .claim("parent_name", student.getParentName())
                            .claim("parent_phone", student.getParentPhone())
                            .claim("points", student.getPoints())
                            .claim("role", "STUDENT")
                            .build();

                } else if (userEntity instanceof User) {
                    User user = (User) userEntity;

                    return OidcUserInfo.builder()
                            .subject(user.getUsername())
                            .name(user.getFullName())
                            .givenName(user.getFirstName())
                            .familyName(user.getLastName())
                            .preferredUsername(user.getUsername())
                            .profile("https://education.co.kr/profile/" + user.getUsername())
                            .picture(user.getProfilePicture() != null ? user.getProfilePicture() :
                                "https://education.co.kr/avatar/" + user.getUsername())
                            .website("https://education.co.kr")
                            .email(user.getEmail())
                            .emailVerified(user.getEmailVerified())
                            .phoneNumber(user.getPhoneNumber())
                            .phoneNumberVerified(user.getPhoneVerified())
                            .zoneinfo("Asia/Seoul")
                            .locale("ko-KR")
                            .updatedAt(user.getUpdatedAt() != null ?
                                user.getUpdatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)
                            .claim("department", user.getDepartment())
                            .claim("employee_id", user.getEmployeeId())
                            .claim("role", "USER")
                            .build();
                }

            } catch (UsernameNotFoundException e) {
                // 사용자를 찾을 수 없는 경우 기본값 반환
                return OidcUserInfo.builder()
                        .subject(loginId)
                        .name(loginId)
                        .email(loginId + "@education.co.kr")
                        .build();
            }

            // 기본값 반환 (예외적인 경우)
            return OidcUserInfo.builder()
                    .subject(loginId)
                    .name(loginId)
                    .email(loginId + "@education.co.kr")
                    .build();
        };
    }
}