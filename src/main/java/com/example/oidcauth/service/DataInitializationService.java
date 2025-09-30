package com.example.oidcauth.service;

import com.example.oidcauth.entity.Branch;
import com.example.oidcauth.entity.Student;
import com.example.oidcauth.entity.User;
import com.example.oidcauth.entity.enums.*;
import com.example.oidcauth.repository.BranchRepository;
import com.example.oidcauth.repository.StudentRepository;
import com.example.oidcauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DataInitializationService implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        // HQ 관리자 생성
        User hqAdmin = new User();
        hqAdmin.setUsername("hq_admin");
        hqAdmin.setPassword(passwordEncoder.encode("admin123"));
        hqAdmin.setEmail("admin@example.com");
        hqAdmin.setFirstName("본사");
        hqAdmin.setLastName("관리자");
        hqAdmin.setPhoneNumber("02-1234-5678");
        hqAdmin.setRole(UserRole.HQ_ADMIN);
        hqAdmin.setDepartment("본사");
        hqAdmin.setEmployeeId("HQ001");
        userRepository.save(hqAdmin);

        // 지점 생성 - 강남지점
        Branch gangnamBranch = new Branch();
        gangnamBranch.setBranchId("GANGNAM001");
        gangnamBranch.setBranchName("강남지점");
        gangnamBranch.setRegionInfo("서울시 강남구");
        gangnamBranch.setRepresentativeName("김강남");
        gangnamBranch.setContactNumber("02-555-1234");
        gangnamBranch.setEmail("gangnam@example.com");
        gangnamBranch.setOperationType(OperationType.FRANCHISE);
        gangnamBranch.setTeachingSubjects("과학,사회");
        gangnamBranch.setAvailableTickets(100);
        gangnamBranch.setBasicAddress("서울시 강남구 테헤란로 123");
        gangnamBranch.setDetailedAddress("ABC빌딩 3층");
        gangnamBranch.setRegistrationDate(LocalDateTime.now().minusMonths(6));
        gangnamBranch.setMemo("강남 지역 대형 지점");
        branchRepository.save(gangnamBranch);

        // 지점 생성 - 성남지점
        Branch seongnamBranch = new Branch();
        seongnamBranch.setBranchId("SEONGNAM001");
        seongnamBranch.setBranchName("성남지점");
        seongnamBranch.setRegionInfo("경기도 성남시 분당구");
        seongnamBranch.setRepresentativeName("이성남");
        seongnamBranch.setContactNumber("031-777-5678");
        seongnamBranch.setEmail("seongnam@example.com");
        seongnamBranch.setOperationType(OperationType.DIRECT);
        seongnamBranch.setTeachingSubjects("과학");
        seongnamBranch.setAvailableTickets(50);
        seongnamBranch.setBasicAddress("경기도 성남시 분당구 판교역로 456");
        seongnamBranch.setDetailedAddress("판교타워 7층");
        seongnamBranch.setRegistrationDate(LocalDateTime.now().minusMonths(3));
        seongnamBranch.setMemo("판교 신도시 지점");
        branchRepository.save(seongnamBranch);

        // 지점 관리자 생성 - 강남지점
        User gangnamManager = new User();
        gangnamManager.setUsername("gangnam_manager");
        gangnamManager.setPassword(passwordEncoder.encode("manager123"));
        gangnamManager.setEmail("manager.gangnam@example.com");
        gangnamManager.setFirstName("김");
        gangnamManager.setLastName("지점장");
        gangnamManager.setPhoneNumber("010-1111-2222");
        gangnamManager.setRole(UserRole.BRANCH_MANAGER);
        gangnamManager.setBranch(gangnamBranch);
        gangnamManager.setDepartment("강남지점");
        gangnamManager.setEmployeeId("GN001");
        userRepository.save(gangnamManager);

        // 지점 관리자 생성 - 성남지점
        User seongnamManager = new User();
        seongnamManager.setUsername("seongnam_manager");
        seongnamManager.setPassword(passwordEncoder.encode("manager123"));
        seongnamManager.setEmail("manager.seongnam@example.com");
        seongnamManager.setFirstName("이");
        seongnamManager.setLastName("지점장");
        seongnamManager.setPhoneNumber("010-3333-4444");
        seongnamManager.setRole(UserRole.BRANCH_MANAGER);
        seongnamManager.setBranch(seongnamBranch);
        seongnamManager.setDepartment("성남지점");
        seongnamManager.setEmployeeId("SN001");
        userRepository.save(seongnamManager);

        // 학생 생성 - 강남지점 정회원
        Student student1 = new Student();
        student1.setLoginId("student001");
        student1.setPassword(passwordEncoder.encode("1234"));
        student1.setAccountType(AccountType.REGULAR);
        student1.setStudentName("김학생");
        student1.setStudentPhone("010-5555-1111");
        student1.setGrade("중학교 2학년");
        student1.setSchool("강남중학교");
        student1.setAddress("서울시 강남구 대치동 123-45");
        student1.setBranch(gangnamBranch);
        student1.setParentName("김학부모");
        student1.setParentPhone("010-5555-0000");
        student1.setSubject(Subject.SCIENCE);
        student1.setMonthlyClasses(8);
        student1.setReadingScore(85);
        student1.setStartSession(1);
        student1.setPoints(150);
        student1.setRegistrationDate(LocalDateTime.now().minusMonths(2));
        student1.setNotes("성실하고 적극적인 학생");
        studentRepository.save(student1);

        // 학생 생성 - 강남지점 예비회원
        Student student2 = new Student();
        student2.setLoginId("student002");
        student2.setPassword(passwordEncoder.encode("5678"));
        student2.setAccountType(AccountType.PRELIMINARY);
        student2.setStudentName("박예비");
        student2.setStudentPhone("010-6666-2222");
        student2.setGrade("중학교 1학년");
        student2.setSchool("선릉중학교");
        student2.setAddress("서울시 강남구 선릉로 567");
        student2.setBranch(gangnamBranch);
        student2.setParentName("박학부모");
        student2.setParentPhone("010-6666-0000");
        student2.setSubject(Subject.SOCIAL);
        student2.setMonthlyClasses(4);
        student2.setReadingScore(0);
        student2.setStartSession(1);
        student2.setPoints(0);
        student2.setRegistrationDate(LocalDateTime.now().minusWeeks(1));
        student2.setNotes("체험 수업 중인 예비 회원");
        studentRepository.save(student2);

        // 학생 생성 - 성남지점 정회원
        Student student3 = new Student();
        student3.setLoginId("student003");
        student3.setPassword(passwordEncoder.encode("9012"));
        student3.setAccountType(AccountType.REGULAR);
        student3.setStudentName("최성남");
        student3.setStudentPhone("010-7777-3333");
        student3.setGrade("고등학교 1학년");
        student3.setSchool("분당고등학교");
        student3.setAddress("경기도 성남시 분당구 정자동 789");
        student3.setBranch(seongnamBranch);
        student3.setParentName("최학부모");
        student3.setParentPhone("010-7777-0000");
        student3.setSubject(Subject.SCIENCE);
        student3.setMonthlyClasses(12);
        student3.setReadingScore(92);
        student3.setStartSession(5);
        student3.setPoints(280);
        student3.setRegistrationDate(LocalDateTime.now().minusMonths(4));
        student3.setNotes("수학, 과학 우수 학생");
        studentRepository.save(student3);

        // 학생 생성 - 성남지점 정회원
        Student student4 = new Student();
        student4.setLoginId("student004");
        student4.setPassword(passwordEncoder.encode("3456"));
        student4.setAccountType(AccountType.REGULAR);
        student4.setStudentName("정판교");
        student4.setStudentPhone("010-8888-4444");
        student4.setGrade("중학교 3학년");
        student4.setSchool("판교중학교");
        student4.setAddress("경기도 성남시 분당구 판교동 321");
        student4.setBranch(seongnamBranch);
        student4.setParentName("정학부모");
        student4.setParentPhone("010-8888-0000");
        student4.setSubject(Subject.SCIENCE);
        student4.setMonthlyClasses(8);
        student4.setReadingScore(78);
        student4.setStartSession(3);
        student4.setPoints(120);
        student4.setRegistrationDate(LocalDateTime.now().minusMonths(1));
        student4.setNotes("집중력이 좋은 학생");
        studentRepository.save(student4);

        System.out.println("샘플 데이터가 성공적으로 생성되었습니다:");
        System.out.println("- HQ 관리자: 1명");
        System.out.println("- 지점: 2개 (강남지점, 성남지점)");
        System.out.println("- 지점 관리자: 2명");
        System.out.println("- 학생: 4명 (정회원 3명, 예비회원 1명)");
    }
}