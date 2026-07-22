package com.flashbite.user.dto;

public final class AuthValidationPatterns {
    public static final String E164_PHONE = "^\\+[1-9]\\d{1,14}$";
    public static final String EMAIL_OR_PHONE =
            "^(?:[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+|\\+[1-9]\\d{1,14})$";
    public static final String STRONG_PASSWORD =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,64}$";

    private AuthValidationPatterns() {
    }
}
