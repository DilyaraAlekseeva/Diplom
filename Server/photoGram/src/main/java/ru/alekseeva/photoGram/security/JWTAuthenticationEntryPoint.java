package ru.alekseeva.photoGram.security;


import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.alekseeva.photoGram.payload.response.InvalidLoginResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//класс JWTAuthenticationEntryPoint, который будет служить нам для того
// чтобы ловить ошибку авторизации и выдавать
// статус 401 (когда пользователь пытается получить защищенный ресурс без авторизации).
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //Отправялем непосредственно ответ при невалидной авторизации.
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse);
        httpServletResponse.setContentType(SecurityConstants.CONTENT_TYPE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().println(jsonLoginResponse);
    }
}
