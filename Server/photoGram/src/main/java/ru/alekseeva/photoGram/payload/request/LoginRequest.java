package ru.alekseeva.photoGram.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty") //username не может быть пустым
    private String username;
    @NotEmpty(message = "Password cannot be empty") //password не может быть пустым
    private String password;

}
