package ru.alekseeva.photoGram;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.internal.matchers.Matches;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import ru.alekseeva.photoGram.entity.User;
import ru.alekseeva.photoGram.security.JWTTokenProvider;
import ru.alekseeva.photoGram.service.UserService;
import ru.alekseeva.photoGram.validations.ResponseErrorValidation;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTwoTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ResponseErrorValidation responseErrorValidation;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_for_signup() throws Exception {

        User newUser = new User("test100@mail.ru", "test100", "test100", "test100", "test1234");
        newUser.setId(100L);

        when(userService.createUser(newUser)).thenReturn(newUser);

        this.mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content("{\"email\" : \"test6@mail.ru\", \"username\" : \"test6\", \"firstname\" : \"test6\", \"lastname\" : \"test6\", \"password\" : \"test1234\", \"confirmPassword\" : \"test1234\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"))
                .andDo(print());

    }
}
