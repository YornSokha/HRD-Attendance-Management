package com.hrd.somchbab.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Bean
    public HttpFirewall allowSlash() {
        return new DefaultHttpFirewall();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .and()
                .authorizeRequests().antMatchers("/admin/**", "/academic/**", "/generation/**", "/course/**", "/classroom/**", "/get-start/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/director/**","/dashboard/**", "/report/**").hasAnyRole("DIRECTOR", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/teacher/**", "/attendance/**").hasAnyRole("TEACHER", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/student/**").hasAnyRole("STUDENT", "TEACHER", "ADMIN")
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").successHandler(authenticationSuccessHandler)
                .failureHandler(failureHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }
}
