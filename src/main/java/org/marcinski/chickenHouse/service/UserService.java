package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.entity.Role;
import org.marcinski.chickenHouse.entity.User;
import org.marcinski.chickenHouse.mapper.UserMapper;
import org.marcinski.chickenHouse.repository.RoleRepository;
import org.marcinski.chickenHouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;
    private UserMapper userMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
    }

    public UserDto findUserByEmail(String email) {
        Optional<User> userByEmail = userRepository.findByEmail(email);
        return userByEmail.map(userMapper::mapTo).orElseThrow(EntityNotFoundException::new);
    }

    public void saveUser(UserDto userDto) {
        if (userDto != null) {
            String email = userDto.getEmail();
            if (!userRepository.findByEmail(email).isPresent()) {
                User user = createNewUser(userDto);
                userRepository.save(user);
            }
        }
    }

    private User createNewUser(UserDto userDto) {
        User user = userMapper.mapTo(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);

        Role userRole = roleRepository.findByRole("USER");
        user.setRole(userRole);
        return user;
    }
}
