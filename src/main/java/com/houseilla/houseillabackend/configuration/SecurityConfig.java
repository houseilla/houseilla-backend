package com.houseilla.houseillabackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    // Reads origins from active property files. Falls back to local Ionic/Vite dev ports if empty.
    @Value("${houseilla.allowed.origins:http://localhost:8100,http://localhost:5173}")
    private String allowedOrigins;

    /**
     * 1. Global HTTP Security Configuration Filter Chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Globally disable CSRF for your headless REST API / Ionic ecosystem requests
                .csrf(csrf -> csrf.disable())

                // Keeps routes accessible during initial build-out phase
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                // Allows the local H2 console web console layout to render within frames seamlessly
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    /**
     * 2. Consolidated Global CORS Infrastructure Mapping
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applies universally across all controller routes
                .allowedOrigins(allowedOrigins.split(",")) // Parses comma-separated values dynamically
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Explicitly opens necessary REST hooks
                .allowedHeaders("*") // Accepts custom authorization or presentation headers
                .allowCredentials(true) // Crucial for passing secure session cookies or JWT tokens
                .maxAge(3600); // Caches preflight OPTIONS responses for 1 hour to lower network overhead
    }
}
