package com.kh.rupp_dev.boukryuniversity.repository;

import com.kh.rupp_dev.boukryuniversity.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void findByFullName_shouldReturnUser_saved() {
        User user = new User();
        user.setFullName("vira");
        user.setPassword("sdfsdf");
        userRepository.save(user);
        Optional<User> user1 = userRepository.findByEmail("vira");
        assertTrue(user1.isPresent());
        Assertions.assertEquals(user1.get().getFullName(), "vira");
    }
}