package com.app.thuvienlichsu.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtility {
    public static String generalizeVietnameseString(String vietnameseString) {
        // Remove accents
        String normalizedString = Normalizer.normalize(vietnameseString, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String withoutAccents = pattern.matcher(normalizedString).replaceAll("");
        String replacedText = withoutAccents.replace("đ", "d").replace("Đ", "d");

        // Convert to lowercase
        String lowercaseString = replacedText.toLowerCase();

        // Remove redundant spaces
        String trimmedString = lowercaseString.trim();

        return trimmedString.replaceAll("\\s+", " ");
    }
    public static int isContain(String str1, String str2) {
        String normalizedStr1 = StringUtility.generalizeVietnameseString(str1);
        String normalizedStr2 = StringUtility.generalizeVietnameseString(str2);
        for (int i = 0; i < Math.min(normalizedStr1.length(), normalizedStr2.length()); i++) {
            if (normalizedStr1.charAt(i) > normalizedStr2.charAt(i)) {
                return 1;
            } else if (normalizedStr1.charAt(i) < normalizedStr2.charAt(i)) {
                return -1;
            }
        }
        if (normalizedStr1.length() > normalizedStr2.length()) {
            return 1;
        }
        return 0;
    }
}
