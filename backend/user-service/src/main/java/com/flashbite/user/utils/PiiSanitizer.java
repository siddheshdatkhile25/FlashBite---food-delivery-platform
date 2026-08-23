package com.flashbite.user.utils;

import java.util.Locale;

public final  class PiiSanitizer {

    private PiiSanitizer() {}

    public static String sanitizeName(String input) {
        if (input == null) {
            return input;
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    public static String sanitizeEmail(String input) {
        if (input == null) {return  input ;}

        return input.trim().toLowerCase(Locale.ROOT);
    }

    public static String sanitizePhone(String input) {
        if (input == null) {return input;}

        return input.trim();
    }
}
