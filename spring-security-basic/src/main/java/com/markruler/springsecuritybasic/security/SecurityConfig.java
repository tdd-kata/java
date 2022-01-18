package com.markruler.springsecuritybasic.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

/**
 * new HttpSecurity() 디버깅
 *
 * @see WebSecurityConfigurerAdapter#getHttp()
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    Logger log = Logger.getLogger(SecurityConfig.class.getName());

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * Session Cookie(JSESSIONID)를 사용하지 않는다.
         * Authorization: Basic <basic64-encoded-key>
         * @see org.springframework.security.web.authentication.www.BasicAuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
         */
        // http.httpBasic();

        http.authorizeRequests()
                /**
                 * @see org.springframework.security.web.authentication.AnonymousAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
                 */
                .anyRequest().authenticated()
        ;

        http.formLogin()
                // .loginPage("/loginPage") // 사용자 정의 로그인 페이지
                .defaultSuccessUrl("/") // 로그인 성공 후 이동
                .failureUrl("/login?error=true") // 로그인 실패 후 이동
                .usernameParameter("userId") // username 파라미터명
                .passwordParameter("passWd") // password 파라미터명
                .loginProcessingUrl("/login") // 로그인 Form Action URL
                .successHandler((request, response, authentication) -> {
                    /**
                     * 로그인 성공 후 핸들러
                     *
                     * @see org.springframework.security.web.authentication.AuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
                     * @see org.springframework.security.web.authentication.AuthenticationFilter#successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)
                     */
                    log.info("Athentication " + authentication.getName());
                    response.sendRedirect("/");
                })
                .failureHandler((request, response, exception) -> {
                    /**
                     * 로그인 실패 후 핸들러
                     *
                     * @see org.springframework.security.web.authentication.AuthenticationFilter#doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain)
                     * @see org.springframework.security.web.authentication.AuthenticationFilter#unsuccessfulAuthentication(HttpServletRequest, HttpServletResponse, AuthenticationException)
                     */
                    log.info("exception : " + exception.getMessage());
                    response.sendRedirect("/login");
                })
        // .permitAll()
        ;

        http.logout()
                .logoutUrl("/logout") // 로그아웃 처리 URL
                .logoutSuccessUrl("/login") // 로그아웃 성공 후 URL
                .deleteCookies("JSESSIONID", "remember-me") // 로그아웃 후 쿠키 처리
                .addLogoutHandler((request, response, authentication) -> {
                    /**
                     * 로그아웃 핸들러
                     *
                     * @see org.springframework.security.web.authentication.logout.LogoutFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
                     */
                    final HttpSession session = request.getSession();
                    session.invalidate();
                })
                .logoutSuccessHandler((request, response, authentication) -> {
                    /**
                     *  로그아웃 성공 후 핸들러
                     *
                     * @see org.springframework.security.web.authentication.logout.DelegatingLogoutSuccessHandler#onLogoutSuccess(HttpServletRequest, HttpServletResponse, Authentication)
                     */
                    log.info("logout success");
                    response.sendRedirect("/login");
                })
                .deleteCookies("remember-me")
        ;

        /**
         * @see org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
         */
        http.rememberMe()
                .rememberMeCookieName("remember") // default: remember-me
                .tokenValiditySeconds(3600) // default: 14일
                .alwaysRemember(true) // remember-me 기능이 활성화되지 않아도 항상 실행
                .userDetailsService(userDetailsService)
        ;

        /**
         * 세션을 관리할 수 있다.
         * ConcurrentSessionFilter는 매 요청마다 현재 사용자의 세션 만료 여부를 체크한다.
         * 만약 세션이 만료되었을 경우 즉시 만료 처리한다.
         *
         * @see org.springframework.security.web.session.SessionManagementFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
         * @see org.springframework.security.web.session.ConcurrentSessionFilter#doFilter(ServletRequest, ServletResponse, FilterChain)
         * @see org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy#onAuthentication(Authentication, HttpServletRequest, HttpServletResponse)
         */
        http.sessionManagement()
                .sessionFixation().changeSessionId() // changeSessionId(default), none, migrateSession, newSession
                /**
                 * Always: 스프링 시큐리티가 항상 세션 생
                 * If_Required: 스프링 시큐리티가 필요 시 생성(default)
                 * Never: 스프링 시큐리티가 생성하지 않지만 이미 존재하면 사용한다.
                 * Stateless: 스프링 시큐리티가 생성하지 않고 존재해도 사용하지 않는다.
                 */
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1) // 최대 허용 세션 수, -1 (무제한 세션 허용)
                .maxSessionsPreventsLogin(true) // 동시 로그인 차단, false: 기존 세션을 만료시킨다(default)
                // .invalidSessionUrl("/invalid") // 세션이 유효하지 않을 때
                .expiredUrl("/expired") // 세션이 만료된 경우 이동할 페이지
        ;
    }

}
