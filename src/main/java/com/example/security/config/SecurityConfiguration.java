package com.example.security.config;

import com.example.security.security.JwtAuthenticationFilter;
import com.example.security.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private JwtAuthenticationFilter jwtAuthFilter;
    private AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
             http
                     .csrf(csrf -> csrf.disable())
                     .authorizeHttpRequests(auth -> auth
                             .requestMatchers(Constants.API_AUTH).permitAll()
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_GET).hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_GET1).hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_USER_PREFIX + "1").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_USER_PREFIX + "2").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_USER_PREFIX + "3").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_USER_PREFIX + "4").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_ADMIN_PREFIX + "1").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_ADMIN_PREFIX + "2").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_ADMIN_PREFIX + "3").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.GET, Constants.API_STUDENT_ADMIN_PREFIX + "4").hasRole(Constants.ROLE_ADMIN)
                             .anyRequest().authenticated()
                     )
                     .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                     .authenticationProvider(authenticationProvider)
                     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    ;


        return http.build();
    }
}
