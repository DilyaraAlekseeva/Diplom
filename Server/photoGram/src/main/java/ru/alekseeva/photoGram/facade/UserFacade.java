package ru.alekseeva.photoGram.facade;

import org.springframework.stereotype.Component;
import ru.alekseeva.photoGram.dto.UserDTO;
import ru.alekseeva.photoGram.entity.User;

@Component
public class UserFacade {

    //метод, который берет обычного пользователя из БД и возвращает на клиента dto-объект
    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());
        return userDTO;
    }

}
