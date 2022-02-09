package ru.alekseeva.photoGram;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.alekseeva.photoGram.entity.User;
import ru.alekseeva.photoGram.payload.request.SignupRequest;
import ru.alekseeva.photoGram.payload.response.MessageResponse;
import ru.alekseeva.photoGram.security.JWTTokenProvider;
import ru.alekseeva.photoGram.service.UserService;
import ru.alekseeva.photoGram.validations.ResponseErrorValidation;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerOneTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @Autowired
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void resetDb() {
        //Здесь можно юзать очистку базы данных.
    }

    @Test
    void test_for_() throws Exception {

        this.mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON).content("{\"username\" : \"test2@mail.ru\", \"password\" : \"test1234\" }"))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    void test_for_signup() throws Exception {

        User newUser = new User();
        this.mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content("{\"email\" : \"test22@mail.ru\", \"username\" : \"test22\", \"firstname\" : \"test22\", \"lastname\" : \"test22\", \"password\" : \"test1234\", \"confirmPassword\" : \"test1234\" }"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message").value("User registered successfully!"))
                .andDo(print());

    }

    //Тест которые делает запрос логин и на вход получает логин и пароль, тест пройден, если есть поле succes со значением true.
    @Test
    void test_for_signin() throws Exception {

        this.mockMvc.perform(post("/api/auth/signin").contentType(MediaType.APPLICATION_JSON).content("{\"username\" : \"test2@mail.ru\", \"password\" : \"test1234\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(print());

    }
}
