package com.example.oidcauth.service;

import com.example.oidcauth.entity.Student;
import com.example.oidcauth.entity.User;
import com.example.oidcauth.entity.enums.UserRole;
import com.example.oidcauth.repository.StudentRepository;
import com.example.oidcauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        // First try to find in Student table
        Optional<Student> studentOpt = studentRepository.findByLoginId(loginId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(student.getLoginId())
                    .password(student.getPassword())
                    .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_STUDENT")))
                    .accountExpired(false)
                    .accountLocked(!student.getActive())
                    .credentialsExpired(false)
                    .disabled(!student.getActive())
                    .build();
        }

        // Then try to find in User table (for branch managers and HQ admins)
        User user = userRepository.findByUsernameOrEmail(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + loginId));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")))
                .accountExpired(false)
                .accountLocked(!user.getEnabled())
                .credentialsExpired(false)
                .disabled(!user.getEnabled())
                .build();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public Student findStudentByLoginId(String loginId) {
        return studentRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found: " + loginId));
    }

    public Object findUserByLoginId(String loginId) {
        Optional<Student> studentOpt = studentRepository.findByLoginIdWithBranch(loginId);
        if (studentOpt.isPresent()) {
            return studentOpt.get();
        }

        Optional<User> userOpt = userRepository.findByUsernameWithBranch(loginId);
        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        throw new UsernameNotFoundException("User not found: " + loginId);
    }
}