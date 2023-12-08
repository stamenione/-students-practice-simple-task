package com.example.MealOrder.enums;

public enum RoleEnum {
    ROLE_ADMIN("role_admin"),
    ROLE_USER("role_user");

    private final String label;

    private RoleEnum(String label) {
        this.label = label;
    }

    public static String getEnumByString(String code) {
        for (RoleEnum e : RoleEnum.values()) {
            if (e.label.equals(code)) return e.name();
        }
        return null;
    }

    public String toString() {
        return label;
    }
}
