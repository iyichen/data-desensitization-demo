package org.example.desensitization.util;

public final class DesensitizationUtils {

    private DesensitizationUtils() {
    }

    public static String desensitize(String text, DesensitizationType type) {
        switch (type) {
            case NAME:
                return new StringBuilder(text).replace(1, 2, "*").toString();
            case ID_CARD:
                return new StringBuilder(text).replace(6, 14, "********").toString();
            case PASSWORD:
                return "******";
            default:
                return text;
        }
    }


    public enum DesensitizationType {
        NAME,
        ID_CARD,
        PASSWORD,
    }
}
