package com.example.oidcauth.config;

import com.example.oidcauth.entity.Branch;
import com.example.oidcauth.entity.Student;
import com.example.oidcauth.entity.enums.AccountType;
import com.example.oidcauth.entity.enums.OperationType;
import com.example.oidcauth.entity.enums.Subject;
import com.example.oidcauth.entity.enums.UserRole;
import com.example.oidcauth.repository.BranchRepository;
import com.example.oidcauth.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// @Component
public class DataInitializer implements CommandLineRunner {

    private final BranchRepository branchRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BranchRepository branchRepository, StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.branchRepository = branchRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (branchRepository.count() == 0) {
            initializeBranches();
        }

        if (studentRepository.count() == 0) {
            initializeStudents();
        }
    }

    private void initializeBranches() {
        // 서울 강남 직영점
        Branch gangnamBranch = new Branch();
        gangnamBranch.setBranchId("SEOUL-GN-001");
        gangnamBranch.setBranchName("강남학습센터");
        gangnamBranch.setRegionInfo("서울시 강남구");
        gangnamBranch.setOperationType(OperationType.DIRECT);
        gangnamBranch.setRepresentativeName("김지영");
        gangnamBranch.setContactNumber("010-1234-5678");
        gangnamBranch.setEmail("manager.gangnam@education.co.kr");
        gangnamBranch.setBasicAddress("서울시 강남구 테헤란로 123번길 45");
        gangnamBranch.setDetailedAddress("교육빌딩 3층");
        gangnamBranch.setAvailableTickets(50);
        gangnamBranch.setActive(true);
        gangnamBranch.setRegistrationDate(LocalDateTime.now());
        gangnamBranch.setCreatedAt(LocalDateTime.now());
        gangnamBranch.setUpdatedAt(LocalDateTime.now());
        branchRepository.save(gangnamBranch);

        // 경기 성남 가맹점
        Branch seongnamBranch = new Branch();
        seongnamBranch.setBranchId("GYEONGGI-SN-002");
        seongnamBranch.setBranchName("성남분당학습센터");
        seongnamBranch.setRegionInfo("경기도 성남시 분당구");
        seongnamBranch.setOperationType(OperationType.FRANCHISE);
        seongnamBranch.setRepresentativeName("이민수");
        seongnamBranch.setContactNumber("010-2345-6789");
        seongnamBranch.setEmail("manager.seongnam@education.co.kr");
        seongnamBranch.setBasicAddress("경기도 성남시 분당구 정자로 567번길 12");
        seongnamBranch.setDetailedAddress("분당타워 8층");
        seongnamBranch.setAvailableTickets(30);
        seongnamBranch.setActive(true);
        seongnamBranch.setRegistrationDate(LocalDateTime.now());
        seongnamBranch.setCreatedAt(LocalDateTime.now());
        seongnamBranch.setUpdatedAt(LocalDateTime.now());
        branchRepository.save(seongnamBranch);
    }

    private void initializeStudents() {
        Branch gangnamBranch = branchRepository.findByBranchId("SEOUL-GN-001").orElse(null);
        Branch seongnamBranch = branchRepository.findByBranchId("GYEONGGI-SN-002").orElse(null);

        // 학생 1: 김민준 (강남센터, 과학 고2)
        Student student1 = new Student();
        student1.setLoginId("student001");
        student1.setPassword(passwordEncoder.encode("1234"));
        student1.setAccountType(AccountType.REGULAR);
        student1.setBranch(gangnamBranch);
        student1.setActive(true);
        student1.setStudentName("김민준");
        student1.setGrade("고2");
        student1.setSchool("압구정고등학교");
        student1.setSubject(Subject.SCIENCE);
        student1.setParentName("김영호");
        student1.setParentPhone("010-3333-4444");
        student1.setAddress("서울시 강남구 압구정로 123");
        student1.setMonthlyClasses(16);
        student1.setPoints(850);
        student1.setRegistrationDate(LocalDateTime.now().minusMonths(6));
        student1.setCreatedAt(LocalDateTime.now().minusMonths(6));
        student1.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student1);

        // 학생 2: 이서연 (성남센터, 사회 고1)
        Student student2 = new Student();
        student2.setLoginId("student002");
        student2.setPassword(passwordEncoder.encode("5678"));
        student2.setAccountType(AccountType.REGULAR);
        student2.setBranch(seongnamBranch);
        student2.setActive(true);
        student2.setStudentName("이서연");
        student2.setGrade("고1");
        student2.setSchool("분당고등학교");
        student2.setSubject(Subject.SOCIAL);
        student2.setParentName("이정민");
        student2.setParentPhone("010-9999-0000");
        student2.setAddress("경기도 성남시 분당구 황새울로 456");
        student2.setMonthlyClasses(12);
        student2.setPoints(720);
        student2.setRegistrationDate(LocalDateTime.now().minusMonths(4));
        student2.setCreatedAt(LocalDateTime.now().minusMonths(4));
        student2.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student2);

        // 학생 3: 박준서 (강남센터, 예비 과학)
        Student student3 = new Student();
        student3.setLoginId("student003");
        student3.setPassword(passwordEncoder.encode("9012"));
        student3.setAccountType(AccountType.PRELIMINARY);
        student3.setBranch(gangnamBranch);
        student3.setActive(true);
        student3.setStudentName("박준서");
        student3.setGrade("중3");
        student3.setSchool("개포중학교");
        student3.setSubject(Subject.SCIENCE);
        student3.setParentName("박성우");
        student3.setParentPhone("010-4444-5555");
        student3.setAddress("서울시 강남구 도곡로 789");
        student3.setMonthlyClasses(8);
        student3.setPoints(650);
        student3.setRegistrationDate(LocalDateTime.now().minusMonths(2));
        student3.setCreatedAt(LocalDateTime.now().minusMonths(2));
        student3.setUpdatedAt(LocalDateTime.now());
        studentRepository.save(student3);
    }
}