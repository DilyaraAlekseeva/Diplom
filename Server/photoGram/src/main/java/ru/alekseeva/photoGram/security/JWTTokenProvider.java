package ru.alekseeva.photoGram.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.alekseeva.photoGram.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {
    //логгер для легкого поиска ошибок
    public static final Logger LOG = LoggerFactory.getLogger(JWTTokenProvider.class);

    //Генерация токена для определенного юзера с claims(дополнительными данными).
    public String generateToken(Authentication authentication) {
        //данные пользователя
        //getPrincipal() возвращает объект, поэтому делаем каст на User
        User user = (User) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        //вычисляем время конца сессии
        Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        //создаем claims - объект, который мы будем передовать в JWT.
        //он будет содержать данные пользователя
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", userId);
        claimsMap.put("username", user.getEmail());
        claimsMap.put("firstname", user.getName());
        claimsMap.put("lastname", user.getLastname());

        return Jwts.builder()
                .setSubject(userId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();

    }

    //Валидация токена
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true; //если все вопрядке, возвращаем true
        }catch (SignatureException |
                MalformedJwtException |
                ExpiredJwtException |
                UnsupportedJwtException |
                IllegalArgumentException ex) {
            LOG.error(ex.getMessage());
            return false; //если возникла ошибка, логгаем ее и возвращаем false
        }
    }

    //Получение id пользователя из токена
    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }

}
