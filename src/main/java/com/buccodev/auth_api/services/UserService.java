package com.buccodev.auth_api.services;

import com.buccodev.auth_api.dto.UserDTO;
import com.buccodev.auth_api.model.User;
import com.buccodev.auth_api.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }



    public UserDTO save(UserDTO userDTO){

        var password = passwordEncoder.encode(userDTO.password());

        var entity = new User(null, userDTO.userName(), userDTO.email(), password, userDTO.role());

        var userSave = userRepository.save(entity);

        return new UserDTO(userSave.getName(), userSave.getEmail(), passwordEncoder.encode(userDTO.password()), userDTO.role());
    }


}
