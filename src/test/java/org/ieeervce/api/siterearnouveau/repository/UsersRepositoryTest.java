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
    
    private static final String PASSWORD = "password";
    private static final byte[] PICTURE = new byte[]{1,2,3};
    private static final String ROLE = "EXAMPLE_ROLE";
    private static final String LASTNAME = "CDE";
    private static final String FIRSTNAME = "ABC";
    private static final String EMAIL = "abc@example.com";
    @Autowired
    UsersRepository usersRepository;
    

    
    @Test
    @Transactional
    void saveUserWorks(){
        var user = new User();
        user.setEmail(EMAIL);
        user.setFirstName(FIRSTNAME);
        user.setLastName(LASTNAME);
        user.setRole(ROLE);
        user.setPicture(PICTURE);
        user.setSocietyId(null);
        user.setPassword(PASSWORD);

        var createdUser = usersRepository.save(user);

        assertThat(createdUser.getUserId()).isNotNull().isInstanceOf(Integer.class);
        assertThat(createdUser.getEmail()).isEqualTo(EMAIL);
        assertThat(createdUser.getFirstName()).isEqualTo(FIRSTNAME);
        assertThat(createdUser.getLastName()).isEqualTo(LASTNAME);
        assertThat(createdUser.getRole()).isEqualTo(ROLE);
        assertThat(createdUser.getPicture()).isEqualTo(PICTURE);
        assertThat(createdUser.getPassword()).isEqualTo(PASSWORD);
        assertThat(createdUser.getSocietyId()).isNull();
    }
}
