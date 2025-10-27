package com.venler42.tamu_dues_api.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // roles that the user has, but there are no roles right now
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // auth can compare passwords
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // accounts don't expire
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // locked accounts are not authenticated
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // if passwords got expired
    }

    @Override
    public boolean isEnabled() {
        return true; // always enabled, never deactivate accounts
    }
}
