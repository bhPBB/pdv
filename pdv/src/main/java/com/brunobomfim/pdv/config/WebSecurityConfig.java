package com.brunobomfim.pdv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .requestMatchers("/login", "/register").permitAll()  // Permite acesso sem login para login e registro
                .anyRequest().authenticated()  // Requer autenticação para qualquer outra URL
            .and()
            .formLogin()
                .loginPage("/login")  // Página personalizada de login
                .permitAll()  // Permite acesso à página de login sem autenticação
            .and()
            .logout()
                .permitAll();  // Permite o logout sem autenticação
        return http.build();  // Retorna a configuração do filtro de segurança
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Codificação de senha usando BCrypt
    }
}
