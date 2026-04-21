package com.rupp.tola.dev.scoring_management_system.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {



    @Test
    void generateOtp() {
        String otp = Util.generateOtp();
        Assert.hasLength(String.valueOf(6), otp);
    }
}