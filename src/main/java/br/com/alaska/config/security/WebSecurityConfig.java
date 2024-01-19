package br.com.alaska.config.security;

import br.com.alaska.service.token.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationService userAuthenticationService;
    private final TokenFilterRequest tokenFilterRequest;

    public WebSecurityConfig(UserAuthenticationService userAuthenticationService, TokenFilterRequest tokenFilterRequest) {
        this.userAuthenticationService = userAuthenticationService;
        this.tokenFilterRequest = tokenFilterRequest;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

       auth.userDetailsService(userAuthenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Desabilita a segurança para todas as URLs
        http.authorizeRequests().anyRequest().permitAll();

        // Desabilita a proteção CSRF (Cross-Site Request Forgery)
        http.csrf().disable();

//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/swagger-ui.html").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
//                .antMatchers(HttpMethod.POST, "/api/v1/user/create").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.addFilterBefore(tokenFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui.html", "/**.html", "/v3/api-docs", "/webjars/**", "/configuration/**", "/swagger-resources/**");
    }
}

