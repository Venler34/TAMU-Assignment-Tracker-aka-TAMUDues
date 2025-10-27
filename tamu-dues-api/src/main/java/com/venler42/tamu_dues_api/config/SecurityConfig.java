package com.venler42.tamu_dues_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.venler42.tamu_dues_api.service.MyUserDetailsService;
import com.venler42.tamu_dues_api.util.JwtAuthenticationFilter;

/* Spring Security Filters permitting access based on path and roles */

@Configuration
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(MyUserDetailsService myUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.myUserDetailsService = myUserDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // make sure these endpoints are publicly available
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizedRequests -> authorizedRequests
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated());
        // .formLogin(form -> form
        // .loginPage("/auth/login")
        // .loginProcessingUrl("/auth/login")
        // .usernameParameter("username")
        // .passwordParameter("password")
        // .defaultSuccessUrl("/v1/users", true)
        // .permitAll())
        // .logout(logout -> logout.permitAll());
        // .userDetailsService(myUserDetailsService);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
