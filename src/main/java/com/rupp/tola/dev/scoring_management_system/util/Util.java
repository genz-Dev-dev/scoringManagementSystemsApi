package com.rupp.tola.dev.scoring_management_system.util;

import java.util.concurrent.ThreadLocalRandom;

public class Util {
    public static String generateOtp() {
        int randomOtp = ThreadLocalRandom.current().nextInt(100000 , 1000000);
        return String.valueOf(randomOtp);
    }
}
