package com.example.servingwebcontent.controller;
import javax.sql.DataSource;

import com.example.servingwebcontent.model.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/questions","/appointments","/question_form","/appointment_form").hasAuthority("USER")
                .antMatchers("/homeadmin","/usersadmin","/chatadmin").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .usernameParameter("email")
                .successHandler((request, response, authentication) -> {
                    CustomUserDetails userDetails= (CustomUserDetails) authentication.getPrincipal();
                    if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")))
                        response.sendRedirect("/homeadmin");
                    else response.sendRedirect("/home");
                })
                .permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }

}