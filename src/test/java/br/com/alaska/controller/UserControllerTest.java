package br.com.alaska.controller;

import br.com.alaska.config.mail.EmailSenderService;
import br.com.alaska.repository.user.UserRepository;
import br.com.alaska.service.token.ConfirmationTokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "url.confirmation-token=http://localhost:8080/api/v1/user/confirmation?token=",
})
class AuthenticationControllerTest {

    private static final String jsonContent = """
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
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ConfirmationTokenService confirmationTokenService;
    @MockBean
    private EmailSenderService emailSenderService;

    @Test
    void mustCreateUserAndReturn200() throws Exception {

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/user/create")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

//    @Test
//    void mustReturn400WhenUsernameAlreadyExists() throws Exception {
//
//        mockMvc
//                .perform(MockMvcRequestBuilders
//                        .post("/api/v1/user/create")
//                        .content(jsonContent)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().is(400));
//
//    }


    //todo ver @ParameterizedTest
    @Test
    void mustReturn400WhenUsernameIsNull() throws Exception {
        var userJson = """
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
            """;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/user/create")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    void mustReturn400WhenInvalidEmail() throws Exception {

        var userJson = """
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
            """;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/user/create")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    @Test
    void mustReturn400WhenPasswordIsNull() throws Exception {

        var userJson = """
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
            """;

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/user/create")
                        .content(userJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

}
