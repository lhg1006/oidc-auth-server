package com.example.oidcauth.entity.enums;

public enum AccountType {
    REGULAR("정식", "정식 회원"),
    PRELIMINARY("예비", "예비 회원");

    private final String displayName;
    private final String description;

    AccountType(String displayName, String description) {
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