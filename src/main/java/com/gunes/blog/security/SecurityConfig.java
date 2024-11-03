package com.gunes.blog.security;


import com.gunes.blog.service.UserService;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // CSRF korumasını devre dışı bırak
                .authorizeHttpRequests(x -> x
                        .requestMatchers("/api/v1/auth/register/**", "/api/v1/auth/login/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/posts/**").permitAll()
                        .requestMatchers("/api/v1/auth/user/**").hasRole("USER")
                        .requestMatchers("/api/v1/posts/**").hasRole("USER")
                        .requestMatchers("/api/v1/auth/admin/**").hasRole("ADMIN")
                )
                .sessionManagement(x -> x.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Oturum yönetimini stateless olarak ayarla
                .authenticationProvider(authenticationProvider()) // Kendi authentication provider'ını kullan
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT filter'ını ekle
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
