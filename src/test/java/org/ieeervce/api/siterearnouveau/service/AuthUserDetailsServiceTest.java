package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.exception.DataExistsException;
import org.ieeervce.api.siterearnouveau.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ieeervce.api.siterearnouveau.service.AuthUserDetailsService.USER_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

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
    void loadValidUserNameGivesAValidUser() {
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        AuthUserDetails userDetails = authUserDetailsService.loadUserByUsername(String.valueOf(USER_ID));
        assertThat(userDetails)
                .isNotNull()
                .extracting(AuthUserDetails::getUser)
                .isSameAs(user);

        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void loadInvalidUserNameGivesAnException() {

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> authUserDetailsService.loadUserByUsername("holdonthisisnotausername"));

        assertThat(exception).hasMessageContaining("Not a valid username");

        verify(usersRepository, never()).findById(anyInt());
    }

    @Test
    void loadMissingUserNameGivesAnException() {
        when(usersRepository.findById(USER_ID)).thenReturn(Optional.empty());
        String userIdString = String.valueOf(USER_ID);
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> authUserDetailsService.loadUserByUsername(userIdString));

        assertThat(exception).hasMessageContaining("Username not found");

        verify(usersRepository).findById(USER_ID);
    }

    @Test
    void testCreateIfNotExists() throws DataExistsException {
        when(usersRepository.existsById(USER_ID)).thenReturn(false);
        User mockUser = getMockUserWithUserId();
        User mockUser2 = getMockUserWithUserId();
        when(usersRepository.save(mockUser)).thenReturn(mockUser2);
        User savedUser = authUserDetailsService.createIfNotExists(mockUser);
        assertThat(savedUser).isSameAs(mockUser2);
    }

    @Test
    void testCreateThrowsExceptionIfExists() {
        when(usersRepository.existsById(USER_ID)).thenReturn(true);
        User mockUser = getMockUserWithUserId();
        assertThatThrownBy(() -> authUserDetailsService.createIfNotExists(mockUser)).isInstanceOf(DataExistsException.class)
                .hasMessage(USER_ALREADY_EXISTS);
        verify(usersRepository, never()).save(any());
    }

    private User getMockUserWithUserId() {
        User user = new User();
        user.setUserId(USER_ID);
        return user;
    }


}
