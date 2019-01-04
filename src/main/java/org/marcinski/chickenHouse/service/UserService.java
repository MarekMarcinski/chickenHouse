package org.marcinski.chickenHouse.service;

import org.marcinski.chickenHouse.dto.UserDto;
import org.marcinski.chickenHouse.entity.Role;
import org.marcinski.chickenHouse.entity.User;
import org.marcinski.chickenHouse.mapper.UserMapper;
import org.marcinski.chickenHouse.repository.RoleRepository;
import org.marcinski.chickenHouse.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;
    private UserMapper userMapper;
    private EmailService emailService;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder encoder,
                       UserMapper userMapper,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.emailService = emailService;
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
                emailService.sendEmailWithAuthorizationLink(email, user.getUuid());
            }
        }
    }

    public void authorizeUser(String uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(EntityNotFoundException::new);
        user.setActive(true);
        userRepository.save(user);
    }

    public void resetPassword(String email) {
        UserDto userByEmail = findUserByEmail(email);

        String newPass = UUID.randomUUID().toString();
        String encodedPass = encoder.encode(newPass);

        userByEmail.setPassword(encodedPass);
        userRepository.save(userMapper.mapTo(userByEmail));

        emailService.sendEmailWithResetPassword(email, newPass);
    }

    private User createNewUser(UserDto userDto) {
        User user = userMapper.mapTo(userDto);
        user.setUuid(UUID.randomUUID().toString());
        user.setPassword(encoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRole("USER");
        user.setRole(userRole);
        return user;
    }
}
