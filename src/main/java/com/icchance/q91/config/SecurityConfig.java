package com.icchance.q91.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/user/register").permitAll()
//                .antMatchers("/user/captcha").permitAll()
//                .anyRequest().authenticated();
                .antMatchers("/captcha/get", "/captcha/check").anonymous()
                .anyRequest().permitAll()
                // 關閉對CSRF攻擊的防護，讓postman或前端可打request
                .and()
                .csrf().disable()
        ;
    }


}
