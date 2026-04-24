package com.kh.rupp_dev.boukryuniversity.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class UtilTest {



    @Test
    void generateOtp() {
        String otp = Util.generateOtp();
        Assert.hasLength(String.valueOf(6), otp);
    }
}