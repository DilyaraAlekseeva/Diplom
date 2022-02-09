package ru.alekseeva.photoGram.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.alekseeva.photoGram.entity.User;
import ru.alekseeva.photoGram.service.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

//Фильтр аутентификации - эта штука не дает заходить на страницы приложения без авторизации.
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    public static final Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    //каждый раз, когда запрос будет поступать на сервер, будет вызываться этот метод и мы будем брать
    //данные из запроса httpServletRequest
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(httpServletRequest);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList()
                );
                //задаем детали авторизации
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));;
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            LOG.error("Could not set user authentication");
        }
        //доавим наш фильтр в цепочку фильтров
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    //Метод, который берет JWT прямо из запроса на сервер.
    private String getJWTFromRequest(HttpServletRequest request) {
        //каждый раз, когда мы будем делать запрос на сервер с ангуляра, мы будем передавать JWT
        //внутри нашего header'а
        String bearToken = request.getHeader(SecurityConstants.HEADER_STRING);
       if (StringUtils.hasText(bearToken) && bearToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
           return bearToken.split(" ")[1];
       }
       return null;
    }


}
