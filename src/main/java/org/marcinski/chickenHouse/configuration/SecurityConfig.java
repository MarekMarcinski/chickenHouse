package org.marcinski.chickenHouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder encoder;

    private ApplicationUserDetailService applicationUserDetailService;

    private AuthenticationFailureHandler failureLoginHandler;

    public SecurityConfig(BCryptPasswordEncoder encoder,
                          ApplicationUserDetailService applicationUserDetailService,
                          AuthenticationFailureHandler failureLoginHandler) {
        this.encoder = encoder;
        this.applicationUserDetailService = applicationUserDetailService;
        this.failureLoginHandler = failureLoginHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/robots", "/robot", "/robot.txt", "/robots.txt").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers("home", "new_house").hasAnyAuthority("USER").anyRequest()
                .authenticated()
                .and()
                .rememberMe()
                .and().csrf().disable().formLogin()
                .loginPage("/login")
                .failureHandler(failureLoginHandler)
                .defaultSuccessUrl("/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/webjars/**");
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(applicationUserDetailService);
        provider.setHideUserNotFoundExceptions(false) ;

        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider()).userDetailsService(applicationUserDetailService);
    }
}