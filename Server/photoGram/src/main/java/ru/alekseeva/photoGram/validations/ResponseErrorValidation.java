package ru.alekseeva.photoGram.validations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;

//валидатор ResponseErrorValidator, который поможет справляться с теми или иными ошибками,
// которые будут приходить на наш сервер. Он будет возвращать нам мап с ошибками, либо же
// ничего не будет возвращать. Принимать он будет BindingResult, который будет приходить из
// Validation framework. Этот BindingResult будет содержать в себе ошибки. Например, когда
// мы будем присылать объект пользователя с пустым логином, то через LoginRequest выскочит
// ошибка “Username cannot be empty”, но прежде чем она куда-либо выскочила мы сначала
// должны ее получить и она приходит именно в объекте BindingResult.
@Service
public class ResponseErrorValidation {

    public ResponseEntity<Object> mapValidationService(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            if (!CollectionUtils.isEmpty(result.getAllErrors())) {
                for (ObjectError error : result.getAllErrors()) {
                    errorMap.put(error.getCode(), error.getDefaultMessage());
                }
            }

            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage()); //в каком поле была ошибка
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
