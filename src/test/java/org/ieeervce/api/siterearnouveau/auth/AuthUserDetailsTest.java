package org.ieeervce.api.siterearnouveau.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.ieeervce.api.siterearnouveau.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailsTest {

    private static final String EXAMPLE_ROLE = "EXAMPLE_ROLE";
    private static final int USER_ID = 123;
    private static final String USER_PASSWORD = "strong_password";
    @Mock
    User user;
    @InjectMocks
    AuthUserDetails authUserDetails;

    @Test
    void testProperties(){
        when(user.getUserId()).thenReturn(USER_ID);
        when(user.getPassword()).thenReturn(USER_PASSWORD);
        when(user.getRole()).thenReturn(EXAMPLE_ROLE);
        assertThat(authUserDetails)
            .isNotNull();

        assertThat(authUserDetails.getUsername())
            .isNotNull()
            .isEqualTo(String.valueOf(USER_ID));
        
        assertThat(authUserDetails.getPassword())
            .isNotNull()
            .isSameAs(USER_PASSWORD);
        
        assertThat(authUserDetails.getAuthorities()).singleElement().isEqualTo(new SimpleGrantedAuthority(EXAMPLE_ROLE));
        
        assertThat(authUserDetails.isAccountNonExpired()).isTrue();
        assertThat(authUserDetails.isAccountNonLocked()).isTrue();
        assertThat(authUserDetails.isCredentialsNonExpired()).isTrue();
        assertThat(authUserDetails.isEnabled()).isTrue();

        verify(user).getUserId();
        verify(user).getPassword();
        verify(user).getRole();
    }

    @Test
    void testCredentialsWipe(){
        authUserDetails.eraseCredentials();
        verify(user).setPassword(null);
    }

}
