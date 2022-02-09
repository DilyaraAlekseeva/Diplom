package ru.alekseeva.photoGram.payload.response;

import lombok.Getter;

//Когда ошибка 401 - то отправляем этот обьект ответом от сервера клиенту.
@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }


}
