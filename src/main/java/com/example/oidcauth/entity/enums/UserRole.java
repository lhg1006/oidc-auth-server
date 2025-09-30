package com.example.oidcauth.entity.enums;

public enum UserRole {
    STUDENT("학생", "실제 학습 서비스를 이용하는 최종 사용자"),
    BRANCH_MANAGER("지점관리자", "실제 학습 현장을 운영하는 현장 관리자"),
    HQ_ADMIN("본사관리자", "전체 시스템의 최고 관리자");

    private final String displayName;
    private final String description;

    UserRole(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}