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
                             .requestMatchers(HttpMethod.GET, "/users/{userId}").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.GET, "/users/{userId}/books").hasAnyRole(Constants.ROLE_ADMIN, Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, "/users/{userId}/borrowed-books").hasAnyRole(Constants.ROLE_ADMIN, Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.POST, "/books/create").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.PUT, "/books/update/{id}").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.DELETE, "/books/delete/{id}").hasRole(Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.GET, "/books/getAll").hasAnyRole(Constants.ROLE_ADMIN, Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.POST, "/books/{bookId}/borrow").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.DELETE, "/books/{bookId}/return").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, "/books/{bookId}/reserve").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, "/books/{bookId}/cancel-reservation").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.POST, "/books/{bookId}/reviews/create").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.GET, "/books/{bookId}/reviews").hasAnyRole(Constants.ROLE_USER, Constants.ROLE_ADMIN)
                             .requestMatchers(HttpMethod.PUT, "/books/reviews/{reviewId}/update").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.DELETE, "/books/reviews/{reviewId}/delete").hasRole(Constants.ROLE_USER)
                             .requestMatchers(HttpMethod.DELETE, "/users/{userId}/history").hasAnyRole(Constants.ROLE_USER, Constants.ROLE_ADMIN)
                             .anyRequest().authenticated()
                     )
                     .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                     .authenticationProvider(authenticationProvider)
                     .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                    ;


        return http.build();
    }
}
