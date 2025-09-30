package com.example.oidcauth.entity;

import com.example.oidcauth.entity.enums.AccountType;
import com.example.oidcauth.entity.enums.Subject;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 계정 정보
    @Column(name = "login_id", unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false, length = 100)
    private String password; // BCrypt encoded password

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    private Boolean active = true;

    // 기본 정보
    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_phone")
    private String studentPhone;

    @Column(nullable = false)
    private String grade; // 학년

    @Column(nullable = false)
    private String school; // 재학 학교

    @Column(columnDefinition = "TEXT")
    private String address;

    // 소속 지점
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    // 학부모 정보
    @Column(name = "parent_name", nullable = false)
    private String parentName;

    @Column(name = "parent_phone", nullable = false)
    private String parentPhone;

    // 학습 설정
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Subject subject;

    @Column(name = "monthly_classes", nullable = false)
    private Integer monthlyClasses; // 월별 수업횟수

    @Column(name = "reading_score")
    private Integer readingScore; // 독해력 평가 점수

    @Column(name = "start_session")
    private Integer startSession; // 시작 회차

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer points = 0;

    // 문서 및 동의서
    @Column(name = "parent_consent_pdf")
    private String parentConsentPdf; // 학부모 동의서 PDF 경로

    @Column(name = "privacy_consent_pdf")
    private String privacyConsentPdf; // 개인정보처리방침 동의서 PDF 경로

    // 추가 정보
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes; // 특이사항 메모

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        // 예비 회원은 비활성화 불가
        if (accountType == AccountType.PRELIMINARY && !active) {
            return;
        }
        this.active = active;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentPhone() {
        return studentPhone;
    }

    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getMonthlyClasses() {
        return monthlyClasses;
    }

    public void setMonthlyClasses(Integer monthlyClasses) {
        this.monthlyClasses = monthlyClasses;
    }

    public Integer getReadingScore() {
        return readingScore;
    }

    public void setReadingScore(Integer readingScore) {
        this.readingScore = readingScore;
    }

    public Integer getStartSession() {
        return startSession;
    }

    public void setStartSession(Integer startSession) {
        this.startSession = startSession;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getParentConsentPdf() {
        return parentConsentPdf;
    }

    public void setParentConsentPdf(String parentConsentPdf) {
        this.parentConsentPdf = parentConsentPdf;
    }

    public String getPrivacyConsentPdf() {
        return privacyConsentPdf;
    }

    public void setPrivacyConsentPdf(String privacyConsentPdf) {
        this.privacyConsentPdf = privacyConsentPdf;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}