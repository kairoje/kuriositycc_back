package com.kuriosity.kcc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/auth/users/login/", "/auth/users/register/").permitAll() // Allow login and registration without authentication
                .antMatchers("/h2-console/**").permitAll() // Allow access to H2 console
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Set session creation policy to STATELESS
                .and()
                .csrf().disable() // Disable CSRF protection
                .headers().frameOptions().disable(); // Disable frame options
        http.cors(); // <==== ADD THIS LINE: Enable CORS (Cross-Origin Resource Sharing)
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter
        return http.build();
    }

}
