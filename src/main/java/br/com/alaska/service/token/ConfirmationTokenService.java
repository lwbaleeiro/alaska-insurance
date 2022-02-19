package br.com.alaska.service.token;

import br.com.alaska.entity.token.ConfirmationToken;

public interface ConfirmationTokenService {

    void save(ConfirmationToken confirmationToken);

    ConfirmationToken findToken(String token);
}
