package com.mindHub.HomeBanking.Utils;

import java.util.*;
import java.util.regex.Pattern;

public class Utils {
    // Get Random number (Account, cards ,etc)
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // Get Random String (reference num transactions)
    public static String getRandomString(int leftLimit, int rightLimit,int targetStringLength, Random random ) {
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    // Get random int for token (email verification)
    private static List<String> createdToken = new ArrayList<>();
    public static String getToken(int leftLimit, int rightLimit,int targetStringLength, Random random){
        String tokenString= "";
        do {
            tokenString = getRandomString(leftLimit,rightLimit, targetStringLength, random);
        } while (createdToken.contains(tokenString));
        createdToken.add(tokenString);
        return tokenString;
    }

    // Ensure UpperCase on Password
    public static Pattern UpperCasePatten = Pattern.compile("[A-Z ]");

}
