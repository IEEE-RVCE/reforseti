package org.ieeervce.api.siterearnouveau.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.ieeervce.api.siterearnouveau.entity.User;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Provide details on User, used by Spring
 */
public class AuthUserDetails implements UserDetails, CredentialsContainer {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(user.getRole())
                .map(SimpleGrantedAuthority::new)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    public AuthUserDetails() {
    }

    public AuthUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        user.setPassword(null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
