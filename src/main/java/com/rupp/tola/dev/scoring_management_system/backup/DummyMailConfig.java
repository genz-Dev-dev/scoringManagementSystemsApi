//package com.rupp.tola.dev.scoring_management_system.config;
//
//import java.io.InputStream;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import jakarta.mail.internet.MimeMessage;
//
//@Configuration
//@Profile("!dev") // active only in dev profile
//public class DummyMailConfig {
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        return new JavaMailSender() {
//
//            @Override
//            public void send(MimeMessage mimeMessage) {}
//
//            @Override
//            public void send(MimeMessage... mimeMessages) {}
//
//            @Override
//            public MimeMessage createMimeMessage() {
//                return new MimeMessage((jakarta.mail.Session) null);
//            }
//
//            @Override
//            public MimeMessage createMimeMessage(InputStream contentStream) {
//                try {
//                    return new MimeMessage(null, contentStream);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void send(SimpleMailMessage... simpleMessages) throws MailException {}
//        };
//    }
//}