package com.example.oidcauth.entity.enums;

public enum OperationType {
    FRANCHISE("가맹점", "가맹점 형태로 운영"),
    DIRECT("직영점", "직영점 형태로 운영"),
    LICENSED("위탁운영", "위탁 운영 형태");

    private final String displayName;
    private final String description;

    OperationType(String displayName, String description) {
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