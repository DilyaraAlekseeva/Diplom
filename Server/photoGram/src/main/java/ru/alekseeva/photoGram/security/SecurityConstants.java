package ru.alekseeva.photoGram.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/auth/*";

    //Требуется для генерации Json Web Token
    public static final String SECRET = "SecretKeyGenJWT";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 6_000_000; //10min - время жизни сессии токена.

}
