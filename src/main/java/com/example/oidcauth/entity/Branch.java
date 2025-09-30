package com.example.oidcauth.entity;

import com.example.oidcauth.entity.enums.OperationType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 계정 정보
    @Column(name = "branch_id", unique = true, nullable = false)
    private String branchId; // 지점/학원 ID (전체 계정 단위 중복체크)

    @Column(nullable = false)
    private Boolean active = true;

    // 기본 정보
    @Column(name = "branch_name", nullable = false)
    private String branchName; // 지점/학원명

    @Column(name = "region_info", nullable = false)
    private String regionInfo; // 지역 정보 (서울시 강남구, 경기도 성남시 등)

    @Column(name = "representative_name", nullable = false)
    private String representativeName; // 대표자명

    @Column(name = "contact_number", nullable = false)
    private String contactNumber; // 연락처

    @Column(nullable = false)
    private String email;

    // 운영 정보
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType; // 운영형태

    @Column(name = "teaching_subjects", columnDefinition = "TEXT")
    private String teachingSubjects; // 교습과목 (JSON 또는 콤마 구분)

    @Column(name = "available_tickets", nullable = false)
    private Integer availableTickets = 0; // 보유 이용권 수

    // 주소 정보
    @Column(name = "basic_address", nullable = false)
    private String basicAddress; // 기본 주소

    @Column(name = "detailed_address")
    private String detailedAddress; // 상세 주소

    // 추가 정보
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate; // 등록일

    @Column(columnDefinition = "TEXT")
    private String memo; // 메모

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 지점에 속한 학생들
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    // 지점 관리자들
    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> branchManagers;

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

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRegionInfo() {
        return regionInfo;
    }

    public void setRegionInfo(String regionInfo) {
        this.regionInfo = regionInfo;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getTeachingSubjects() {
        return teachingSubjects;
    }

    public void setTeachingSubjects(String teachingSubjects) {
        this.teachingSubjects = teachingSubjects;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getBasicAddress() {
        return basicAddress;
    }

    public void setBasicAddress(String basicAddress) {
        this.basicAddress = basicAddress;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<User> getBranchManagers() {
        return branchManagers;
    }

    public void setBranchManagers(List<User> branchManagers) {
        this.branchManagers = branchManagers;
    }
}