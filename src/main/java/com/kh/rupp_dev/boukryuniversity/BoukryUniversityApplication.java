package com.kh.rupp_dev.boukryuniversity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
public class BoukryUniversityApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoukryUniversityApplication.class, args);
	}
}
