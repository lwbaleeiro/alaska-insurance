package br.com.alaska.service.user.impl;

import br.com.alaska.config.mail.EmailBuilderHtml;
import br.com.alaska.config.mail.EmailSenderService;
import br.com.alaska.controllers.user.form.CreateUserForm;
import br.com.alaska.controllers.user.response.UserResponse;
import br.com.alaska.converters.user.UserConverter;
import br.com.alaska.entity.token.ConfirmationToken;
import br.com.alaska.entity.user.User;
import br.com.alaska.exceptions.user.*;
import br.com.alaska.repository.user.UserRepository;
import br.com.alaska.service.token.ConfirmationTokenService;
import br.com.alaska.service.user.EmailValidatorService;
import br.com.alaska.service.user.UserService;
import br.com.alaska.service.user.ValidarCpfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;
    private final EmailBuilderHtml emailBuilderHtml;
    private final EmailValidatorService emailValidatorService;

    @Value("${url.activation-email}")
    private String urlActivationEmail;

    public UserServiceImpl(UserRepository userRepository,
                           ConfirmationTokenService confirmationTokenService,
                           EmailValidatorService emailValidatorService,
                           EmailSenderService emailSenderService,
                           EmailBuilderHtml emailBuilderHtml) {

        this.userRepository = userRepository;
        this.emailValidatorService = emailValidatorService;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
        this.emailBuilderHtml = emailBuilderHtml;
        this.userConverter = new UserConverter(new BCryptPasswordEncoder());
    }

    @Override
    public UserResponse createUser(CreateUserForm createUserForm) {

        log.info(" createUser");
        User user = userConverter.convert(createUserForm);

        this.checkUserEmailIsValid(user);
        this.checkUserCpfIsValid(user);

        Optional<User> optionalUser = userRepository.findByCpf(user.getCpf());
        this.checkUserAlreadyExistsCpf(optionalUser);

        Optional<User> optionalUserEmail = userRepository.findByEmail(user.getEmail());
        this.checkUserAlreadyExistsEmail(optionalUserEmail);

        try {

            userRepository.save(user);

        } catch (Exception error) {
            log.error("error: {}", error.getMessage());
            throw new CreateUserDatabaseException();
        }

        String token = UUID.randomUUID().toString();
        log.info(" token {}", token);
        confirmationTokenService.save(new ConfirmationToken(token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user));

        String email = emailBuilderHtml.buildEmail(user.getName(), urlActivationEmail + token);
        emailSenderService.send(user.getEmail(), email);

        return new UserResponse(user.getId(),
                user.getName(),
                user.getCpf(),
                user.getSex(),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getCellphone());
    }

    @Override
    public void confirmation(String token) {
        log.info(" confirmation token {}", token);

        ConfirmationToken confirmationToken = confirmationTokenService.findToken(token);
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.save(confirmationToken);

        Optional<User> optionalUser = userRepository.findById(confirmationToken.getUser().getId());
        //TODO criar conta após ativar usuário
        optionalUser.ifPresent(user -> {
            log.info("user account enabled!");
            user.setEnabled(Boolean.TRUE);

            try {
                userRepository.save(user);

            } catch (Exception error) {
                log.error("error: {}", error.getMessage());
                throw new CreateUserDatabaseException();
            }
        });
    }

    @Override
    public UserResponse alterUser(CreateUserForm createUserForm) {

        log.info(" alter user");
        User user = userConverter.convert(createUserForm);

        Optional<User> userFound = userRepository.findById(user.getId());
        if (userFound.isEmpty()) {
            throw new UserNotFoundByIdException();
        }

        this.checkUserEmailIsValid(user);
        this.checkUserCpfIsValid(user);
        try {
            User userAlter = userRepository.save(user);

            return new UserResponse(userAlter.getId(),
                    userAlter.getName(),
                    userAlter.getCpf(),
                    userAlter.getSex(),
                    userAlter.getDateOfBirth(),
                    userAlter.getEmail(),
                    userAlter.getCellphone());

        } catch (Exception error) {
            log.error("error: {}", error.getMessage());
            throw new CreateUserDatabaseException();
        }
    }

    @Override
    public void inactivate(String id) {

        log.info(" inactivate {} ", id);

        Optional<User> optionalUser = userRepository.findById(Long.valueOf(id));
        User user = optionalUser.orElseThrow(UserNotFoundByIdException::new);
        user.setEnabled(Boolean.FALSE);

        try {
            userRepository.save(user);

        } catch (Exception error) {
            log.error("error: {}", error.getMessage());
            throw new CreateUserDatabaseException();
        }
    }

    @Override
    public UserResponse findUserById(String id) {

        log.info(" findUserById {} ", id);

        Optional<User> optionalUser = userRepository.findById(Long.valueOf(id));

        User user = optionalUser.orElseThrow(UserNotFoundByIdException::new);

        return new UserResponse(user.getId(),
                user.getName(),
                user.getCpf(),
                user.getSex(),
                user.getDateOfBirth(),
                user.getEmail(),
                user.getCellphone());
    }

    @Override
    public List<UserResponse> findAll() {

        log.info(" findAll");

        List<UserResponse> userResponses = new ArrayList<>();
        List<User> all = userRepository.findAll();

        for (User user : all) {
            userResponses.add(new UserResponse(user.getId(),
                    user.getName(),
                    user.getCpf(),
                    user.getSex(),
                    user.getDateOfBirth(),
                    user.getEmail(),
                    user.getCellphone()));
        }

        return userResponses;
    }

    private void checkUserAlreadyExistsEmail(Optional<User> optionalUserEmail) {
        if (optionalUserEmail.isPresent()) {
            throw new UserAlreadyExistsWithEmailException();
        }
    }

    private void checkUserAlreadyExistsCpf(Optional<User> optionalUser) {
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistsWithCpfException();
        }
    }

    private void checkUserEmailIsValid(User user) {
        if (!emailValidatorService.test(user.getEmail())) {
            throw new UserEmailNotValidException();
        }
    }

    private void checkUserCpfIsValid(User user) {
        if (!ValidarCpfService.valid(user.getCpf())) {
            throw new UserCpfIsNotValidException();
        }
    }
}
