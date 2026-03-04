package lk.icbt.oceanview.model;

public enum UserRole {
    ADMIN, STAFF;

    public static UserRole from(String value) {
        if (value == null) throw new IllegalArgumentException("role is required");
        try {
            return UserRole.valueOf(value.trim().toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid role");
        }
    }
}
