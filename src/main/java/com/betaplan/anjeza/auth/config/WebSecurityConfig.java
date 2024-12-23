package com.betaplan.anjeza.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebSecurityConfig {

    @Autowired
    HandlerMappingIntrospector introspector;

    private UserDetailsService userDetailsService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // authorize or restrict access based on requests
                .authorizeHttpRequests(
                        auth -> auth
//                                .requestMatchers(
//                                        new MvcRequestMatcher(introspector, "/home")
//                                ).hasRole("ADMIN")
                                .requestMatchers(
                                        // match all endpoints '/'
                                        new MvcRequestMatcher(introspector, "/"),
                                        // match '/home' endpoint
                                        new MvcRequestMatcher(introspector, "/home")
                                )
                                // authenticate before trying to access
                                .authenticated()
//                                 any other request different from above
                                .anyRequest()
                                // provide access to everyone
                                .permitAll()
                )
                // users are going to be authenticated via a login FORM
                .formLogin(
                        form -> // specify URL where users are redirected to login
                                form.loginPage("/login")
                                        // where to go if loggin is successfull
                                        .defaultSuccessUrl("/home")
                                        // allow every user to access login page
                                        .permitAll()
                )
                // provide logging out support
                .logout(
                        logout -> logout.permitAll()
                );
        return http.build();
    }

    // Add BCrypt Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure Spring Security to use our custom implementation of UserDetailsService with BCrypt encoder
     *
     * @param auth authentication manager builder
     * @throws Exception if an error occurs when adding UserDetailsService
     */
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}