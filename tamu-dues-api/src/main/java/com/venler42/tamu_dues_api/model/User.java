package com.venler42.tamu_dues_api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/* Logging In Spring Security */
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Users", schema = "TAMUDues")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // identity says don't try to manage, let database generate
                                                        // value using auto_increment
    private Integer id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // delete from database if parent
                                                                                   // doesn't reference it
    @JsonIgnore // avoid infinite recuriosn because references
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // doesn't include in response
    private List<Section> sections;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    // return Collections.emptyList();
    // }

    // @Override
    // public boolean isAccountNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isAccountNonLocked() {
    // return true;
    // }

    // @Override
    // public boolean isCredentialsNonExpired() {
    // return true;
    // }

    // @Override
    // public boolean isEnabled() {
    // return true;
    // }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
