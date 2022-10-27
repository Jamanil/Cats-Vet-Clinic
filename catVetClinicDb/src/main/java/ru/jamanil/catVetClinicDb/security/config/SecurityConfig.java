package ru.jamanil.catVetClinicDb.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.jamanil.catVetClinicDb.security.staff.services.StaffDetailsService;

/**
 * @author Victor Datsenko
 * 24.10.2022
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(HttpSecurity httpSecurity,
                                                          StaffDetailsService detailsService) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(detailsService)
                .passwordEncoder(getPasswordEncoder())
                .and().build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/auth/failure_login").permitAll()
                .antMatchers("/admin/*", "/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/client", true)
                .failureUrl("/auth/failure_login")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");

        return httpSecurity.build();
    }
}
