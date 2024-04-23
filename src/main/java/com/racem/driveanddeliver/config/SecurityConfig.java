package com.racem.driveanddeliver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF
                .csrf(AbstractHttpConfigurer::disable)

                // Authorize Requests
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())

                // HTTP Basic Authentication
                .httpBasic(withDefaults());

        return http.build();
    }
}
