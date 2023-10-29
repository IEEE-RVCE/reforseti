package org.ieeervce.api.siterearnouveau.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailsServiceTest {
    private static final int USER_ID = 1234;

    @Mock
    UsersRepository usersRepository;
    @Mock
    User user;

    @InjectMocks
    AuthUserDetailsService authUserDetailsService;

    @Test
    void loadValidUserNameGivesAValidUser(){
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        AuthUserDetails userDetails = authUserDetailsService.loadUserByUsername(String.valueOf(USER_ID));
        Assertions.assertThat(userDetails)
                    .isNotNull()
                    .extracting(e->e.getUser())
                    .isSameAs(user);

        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void loadInvalidUserNameGivesAnException(){

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,()->{
            authUserDetailsService.loadUserByUsername("holdonthisisnotausername");
        });
        
        Assertions.assertThat(exception).hasMessageContaining("Not a valid username");

        verify(usersRepository,never()).findById(anyInt());
    }

    @Test
    void loadMissingUserNameGivesAnException(){
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,()->{
            authUserDetailsService.loadUserByUsername(String.valueOf(USER_ID));
        });
        
        Assertions.assertThat(exception).hasMessageContaining("Username not found");

        verify(usersRepository).findById(USER_ID);
    }


}
