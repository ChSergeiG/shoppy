package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.UserRepository;
import ru.chsergeig.shoppy.dto.UserDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Order;
import ru.chsergeig.shoppy.jooq.tables.pojos.User;
import ru.chsergeig.shoppy.mapping.UserMapper;
import ru.chsergeig.shoppy.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.fetchByStatus(Status.ADDED, Status.ACTIVE, Status.DISABLED);
        return users.stream()
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO addUser(String name) {
        User pojo = new User(null, name,  null, Status.ADDED);
        userRepository.insert(pojo);
        return userMapper.map(pojo);
    }

    @Override
    public UserDTO addUser(UserDTO dto) {
        User pojo = userMapper.map(dto);
        userRepository.insert(pojo);
        return userMapper.map(pojo);
    }

    @Override
    public UserDTO updateUser(UserDTO dto) {
        User pojo = userMapper.map(dto);
        userRepository.update(pojo);
        return userMapper.map(pojo);
    }

    @Override
    public Integer deleteUser(String name) {
        List<User> users = userRepository.fetchByName(name);
        users.forEach(o -> o.setStatus(Status.REMOVED));
        userRepository.update(users);
        return users.size();
    }
}
