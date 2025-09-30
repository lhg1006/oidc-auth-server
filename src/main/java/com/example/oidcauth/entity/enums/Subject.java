package com.example.oidcauth.entity.enums;

public enum Subject {
    SCIENCE("과학", "과학 과목"),
    SOCIAL("사회", "사회 과목");

    private final String displayName;
    private final String description;

    Subject(String displayName, String description) {
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