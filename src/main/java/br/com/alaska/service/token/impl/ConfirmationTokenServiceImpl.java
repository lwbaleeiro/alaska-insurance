package br.com.alaska.service.token.impl;

import br.com.alaska.entity.token.ConfirmationToken;
import br.com.alaska.exceptions.token.CreateTokenDatabaseException;
import br.com.alaska.exceptions.token.TokenAlreadyConfirmedException;
import br.com.alaska.exceptions.token.TokenDoNotExistsException;
import br.com.alaska.exceptions.token.TokenExpiredException;
import br.com.alaska.repository.token.ConfirmationTokenRepository;
import br.com.alaska.service.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void save(ConfirmationToken confirmationToken) {

        try {
            confirmationTokenRepository.save(confirmationToken);
        } catch (Exception error) {
            log.error("error: {}", error.getMessage());
            throw new CreateTokenDatabaseException();
        }
    }

    @Override
    public ConfirmationToken findToken(String token) {

        log.info(" findToken {}", token);

        Optional<ConfirmationToken> tokenOptional = confirmationTokenRepository.findByToken(token);

        this.checkTokenExists(tokenOptional);
        this.checkTokenExpired(tokenOptional);
        this.checkTokenAlreadyConfirmed(tokenOptional);

        return tokenOptional.get();
    }

    private void checkTokenExists(Optional<ConfirmationToken> tokenOptional) {
        if (tokenOptional.isEmpty()) {
            throw new TokenDoNotExistsException();
        }
    }

    private void checkTokenExpired(Optional<ConfirmationToken> tokenOptional) {
        if (tokenOptional.isPresent() && tokenOptional.get().getExpiresAt().isBefore(LocalDateTime.now())) {
                throw new TokenExpiredException();
        }
    }

    private void checkTokenAlreadyConfirmed(Optional<ConfirmationToken> tokenOptional) {
        if (tokenOptional.isPresent() && tokenOptional.get().getConfirmedAt() != null) {
            throw new TokenAlreadyConfirmedException();
        }
    }
}
