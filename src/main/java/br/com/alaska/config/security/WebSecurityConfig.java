package br.com.alaska.config.security;

import br.com.alaska.service.token.UserAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final UserAuthenticationService userAuthenticationService;

    public WebSecurityConfig(UserAuthenticationService userAuthenticationService, TokenFilterRequest tokenFilterRequest) {
        this.userAuthenticationService = userAuthenticationService;
    }

//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//       auth.userDetailsService(userAuthenticationService).passwordEncoder(new BCryptPasswordEncoder());
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable).sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers( "/**.html", "/v3/api-docs", "/webjars/**", "/configuration/**" );
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

