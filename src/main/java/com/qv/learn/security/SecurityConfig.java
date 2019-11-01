package com.qv.learn.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure( HttpSecurity http ) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .and()
                .formLogin()
                .loginPage( "/login" )
                .defaultSuccessUrl( "/admin" )
                .failureUrl( "/login-failed" )
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout" ) )
                .deleteCookies( "JESSIONID" )
                .invalidateHttpSession( true )
                .and()
                .rememberMe()
                .key( "p`UxPBPhC!FK'SH" )
                .tokenValiditySeconds( 86400*7 );

    }
}