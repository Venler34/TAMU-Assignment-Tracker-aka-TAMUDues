package com.venler42.tamu_dues_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.venler42.tamu_dues_api.util.JwtAuthenticationFilter;

/* Spring Security Filters permitting access based on path and roles */

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfig corsConfig;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CorsConfig corsConfig) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.corsConfig = corsConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // make sure these endpoints are publicly available
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizedRequests -> authorizedRequests
                        .requestMatchers("/v1/auth/**").permitAll()
                        .anyRequest().authenticated());

        // Applies CORS To filter, web config only works on controller layer
        http.cors(cors -> cors.configurationSource(request -> {
            CorsRegistry registry = new CorsRegistry();
            corsConfig.addCorsMappings(registry);

            // Convert CorsRegistry to CorsConfiguration
            org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:3000"));
            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            config.setAllowedHeaders(List.of("*"));
            config.setAllowCredentials(true);
            return config;
        }));

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
