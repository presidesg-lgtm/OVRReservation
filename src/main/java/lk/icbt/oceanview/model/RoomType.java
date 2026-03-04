package lk.icbt.oceanview.model;

import lk.icbt.oceanview.exception.ValidationException;

public enum RoomType {
    STANDARD, DELUXE, SUITE;

    public static RoomType from(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("roomType is required");
        }
        try {
            return RoomType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ValidationException("Invalid roomType. Use STANDARD, DELUXE, or SUITE");
        }
    }
}
