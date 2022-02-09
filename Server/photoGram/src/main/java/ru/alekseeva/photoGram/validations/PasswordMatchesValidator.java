package ru.alekseeva.photoGram.validations;

import ru.alekseeva.photoGram.annotations.PasswordMatches;
import ru.alekseeva.photoGram.payload.request.SignupRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    //Валидация двух паролей при создании аккаунта (1 - начальный пароль, 2 - подтверждение пароля)
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest userSignupRequest = (SignupRequest) obj;
        return userSignupRequest.getPassword().equals(userSignupRequest.getConfirmPassword());
    }
}
