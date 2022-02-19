package br.com.alaska.service.token.impl;

import br.com.alaska.entity.user.User;
import br.com.alaska.service.token.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    public String generateToken(Authentication authentication) {

        User logado = (User) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return Jwts.builder()
                .setIssuer("Alaska Insurance API")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    @Override
    public String getRoles(Authentication authentication) {
        User logado = (User) authentication.getPrincipal();
        return logado.getUsersRole().name();
    }

    public Long getIdUsuario(String token) {

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        String[] chunks2 = payload.split(":");
        String[] a = chunks2[2].split(",");
        String sub = a[0];

        return Long.parseLong(sub.replaceAll("^\"|\"$", ""));
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
