package com.markruler.springsecuritybasic.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class AuthorizationConfig extends WebSecurityConfigurerAdapter {

    Logger log = Logger.getLogger(AuthorizationConfig.class.getName());

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailsService 대신 사용 가능
        auth.inMemoryAuthentication().withUser("user").password("{noop}1111").roles("USER");
        auth.inMemoryAuthentication().withUser("sys").password("{noop}1111").roles("SYS");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}1111").roles("ADMIN", "SYS");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * @see org.springframework.security.web.authentication.AnonymousAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
         */
        http.authorizeRequests()
                .antMatchers("/login", "/authentication").permitAll()
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/admin/pay").hasRole("ADMIN")
                .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().authenticated()
        ;

        http.formLogin()
                .defaultSuccessUrl("/") // 로그인 성공 후 이동
                .loginProcessingUrl("/authentication") // 로그인 Form Action URL
                .successHandler((request, response, authentication) -> {
                    RequestCache requestCache = new HttpSessionRequestCache();
                    SavedRequest savedRequest = requestCache.getRequest(request, response);
                    String redirectUrl = savedRequest.getRedirectUrl();
                    response.sendRedirect(redirectUrl);
                })
        ;

        // http.exceptionHandling()
        //         .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/login"))
        //         .accessDeniedHandler(((request, response, accessDeniedException) -> response.sendRedirect("/denied")))
        // ;
    }
}
