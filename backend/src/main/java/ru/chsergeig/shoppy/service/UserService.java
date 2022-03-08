package ru.chsergeig.shoppy.service;

import ru.chsergeig.shoppy.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();

    UserDTO addUser(String name);

    UserDTO addUser(UserDTO dto);

    UserDTO updateUser(UserDTO dto);

    Integer deleteUser(String name);

}
