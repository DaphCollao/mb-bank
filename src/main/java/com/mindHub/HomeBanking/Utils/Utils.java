package com.mindHub.HomeBanking.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utils {
    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static String getRandomString(int leftLimit, int rightLimit,int targetStringLength, Random random ) {
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
