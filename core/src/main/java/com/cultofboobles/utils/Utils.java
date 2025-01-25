package com.cultofboobles.utils;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {

    public static int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}
