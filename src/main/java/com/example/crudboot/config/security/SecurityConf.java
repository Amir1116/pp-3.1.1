package com.example.crudboot.config.security;

import com.example.crudboot.config.security.handler.AuthenticationSuccessHandlerImpl;
import com.example.crudboot.dao.UserDao;
import com.example.crudboot.dao.UserDaoImpl;
import com.example.crudboot.services.UserDetailsServiceImpl;
import com.example.crudboot.services.UserService;
import com.example.crudboot.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConf
        extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

    @Bean
    @Override
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/{id}/edit").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/login").permitAll()
                .antMatchers("/", "/register/**").anonymous()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/register")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .logoutSuccessUrl("/")
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/");

    }
}

