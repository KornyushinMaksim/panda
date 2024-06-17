package com.panda.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    /**
     *
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            Set<String> roles = AuthorityUtils
                .authorityListToSet(authentication
                                    .getAuthorities());

            if (roles.contains("ROLE_ADM")) {
                response.sendRedirect("/admin"); //доступ к странице админа для админов
                return;
            } else if (roles.contains("ROLE_USR")) {
                response.sendRedirect("/user");   //доступ к странице юзера для юзеров
                return;
            }

            response.sendRedirect("/denied");
        };
    }

    @Bean
    public AccessDeniedHandler deniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/denied");
        };
    }

    @Bean
    public AuthenticationManager authManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        System.out.println("authProvider");

        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/lo").permitAll()
                        .requestMatchers("/admin").hasRole("ADM")
                        .requestMatchers("/user").hasRole("USR")
                        .anyRequest().authenticated()
                )
                .formLogin(securityFormLoginConfigurer -> securityFormLoginConfigurer
                        .loginPage("/lo")
                        .successHandler(successHandler())
                        .permitAll()
                )
//                .formLogin( AbstractAuthenticationFilterConfigurer::permitAll)
                .userDetailsService(userDetailsService)
                .exceptionHandling(exception ->
                        exception.accessDeniedHandler(deniedHandler()))
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}
