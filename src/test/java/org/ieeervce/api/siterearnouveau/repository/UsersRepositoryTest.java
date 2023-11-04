package org.ieeervce.api.siterearnouveau.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.ieeervce.api.siterearnouveau.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.transaction.Transactional;

@SpringBootTest()
@ActiveProfiles("test")
class UsersRepositoryTest {
    
    @Autowired
    UsersRepository usersRepository;
    

    
    @Test
    @Transactional
    void saveUserWorks(){
        var user = new User();
        user.setEmail("abc@example.com");
        user.setFirstName("ABC");
        user.setLastName("CDE");
        user.setRole("EXAMPLE_ROLE");
        user.setPicture(new byte[]{1,2,3});
        user.setSocietyId(null);
        user.setPassword("password");

        var createdUser = usersRepository.save(user);

        assertThat(createdUser).extracting(e->e.getUserId()).isNotNull().isInstanceOf(Integer.class);
    }
}
