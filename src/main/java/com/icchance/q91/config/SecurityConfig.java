package com.icchance.q91.config;

import com.icchance.q91.entity.security.UnauthEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    public SecurityConfig (UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/*        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/user/register").permitAll()
//                .antMatchers("/user/captcha").permitAll()
//                .anyRequest().authenticated();
                .antMatchers("/captcha/get", "/captcha/check").anonymous()
                .anyRequest().permitAll()
                // 關閉對CSRF攻擊的防護，讓postman或前端可打request
                .and()
                .csrf().disable()*/
        http.cors().and()
                .csrf().disable()
                //.exceptionHandling().authenticationEntryPoint(new UnauthEntryPoint()).and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/**").permitAll();
                //.anyRequest().authenticated();
        ;
    }

    @Override
    // 有@Bean才能建立出元件注入JWTService
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
