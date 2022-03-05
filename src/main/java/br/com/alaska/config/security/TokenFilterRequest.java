package br.com.alaska.config.security;

import br.com.alaska.service.token.TokenService;
import br.com.alaska.service.token.UserAuthenticationService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilterRequest extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserAuthenticationService userAuthenticationService;

    public TokenFilterRequest(TokenService tokenService, UserAuthenticationService userAuthenticationService) {
        this.tokenService = tokenService;
        this.userAuthenticationService = userAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            var jwtToken = authorizationHeader.substring(7);
            var username = tokenService.extractUsername(jwtToken);

            UserDetails currentUserDetails = userAuthenticationService.loadUserByUsername(username);
            var validToken = tokenService.validateToken(jwtToken, currentUserDetails);

            if (Boolean.TRUE.equals(validToken)) {

                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        currentUserDetails,
                        null,
                        currentUserDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
