package com.calemi.ccore.api.string;

import java.text.DecimalFormat;

/**
 * Use this class to help format strings.
 */
public class StringHelper {

    /**
     * Surrounds the string with brackets.
     * @param str The string to be boxed.
     * @return a boxed string (like [str])
     */
    public static String boxString(String str) {
        return "[" + str + "]";
    }

    /**
     * @param amount The number to insert commas in.
     * @return A number with commas (like 1,000,000)
     */
    public static String insertCommas(int amount) {

        String number = String.valueOf(amount);
        double amountD = Double.parseDouble(number);
        DecimalFormat formatter = new DecimalFormat("#,###");

        return formatter.format(amountD);
    }

    public static String camelToTitle(String camelCaseInput) {

        String spaced = camelCaseInput.replaceAll("([a-z])([A-Z])", "$1 $2")
                .replaceAll("([A-Z])([A-Z][a-z])", "$1 $2");

        String[] words = spaced.split(" ");
        StringBuilder title = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                title.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }

        return title.toString().trim();
    }

    public static String camelToSnake(String camelCaseInput) {
        return camelCaseInput.replaceAll("([a-z])([A-Z])", "$1_$2")
                .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .toLowerCase();
    }
}