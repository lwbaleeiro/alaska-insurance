package br.com.alaska.controller;

import br.com.alaska.config.mail.EmailSenderService;
import br.com.alaska.repository.user.UserRepository;
import br.com.alaska.service.token.ConfirmationTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "url.activation-email=http://localhost:8080/api/v1/user/confirmation?token=",
})
class AuthenticationControllerTest {


    private final MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ConfirmationTokenService confirmationTokenService;
    @MockBean
    private EmailSenderService emailSenderService;

    AuthenticationControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    static Stream<Arguments> requestJsonAndStatus() {
        return Stream.of(
                arguments("""
                        {
                          "name": "Fulano de Tal da Silva",
                          "cpf": "24173578016",
                          "sex": "MALE",
                          "dateOfBirth": "1990-01-01",
                          "email": "26d681d8c76a4bdfb7bb5086178a5f53@testemock.com.io",
                          "cellphone": "000000000",
                          "password": "string",
                          "usersRole": "USER",
                          "enabled": true
                        }
                        """, 200),
                arguments("""
                        {
                          "name": "Fulano de Tal da Silva",
                          "cpf": "24173578016",
                          "sex": "MALE",
                          "dateOfBirth": "1990-01-01",
                          "email": "",
                          "cellphone": "000000000",
                          "password": "string",
                          "usersRole": "USER",
                          "enabled": true
                        }
                        """, 400),
                arguments("""
                        {
                          "name": "Fulano de Tal da Silva",
                          "cpf": "24173578016",
                          "sex": "MALE",
                          "dateOfBirth": "1990-01-01",
                          "email": "mock",
                          "cellphone": "000000000",
                          "password": "string",
                          "usersRole": "USER",
                          "enabled": true
                        }
                        """, 400),
                arguments("""
                        {
                          "name": "Fulano de Tal da Silva",
                          "cpf": "24173578016",
                          "sex": "MALE",
                          "dateOfBirth": "1990-01-01",
                          "email": "26d681d8c76a4bdfb7bb5086178a5f53@testemock.com.io",
                          "cellphone": "000000000",
                          "password": "",
                          "usersRole": "USER",
                          "enabled": true
                        }
                        """, 400)
        );
    }

    @ParameterizedTest
    @MethodSource("requestJsonAndStatus")
    void testCreateNewUser(String requestJson, Integer status) throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/user/create")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(status));
    }
}
