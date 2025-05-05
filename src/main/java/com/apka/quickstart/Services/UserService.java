package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.DTO.UserMapper;
import com.apka.quickstart.DTO.UserResponseDTO;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder,UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponseDTO registerUser(RegisterRequestDTO registerRequest){
        User user = userMapper.registerRequestToUser(registerRequest);
        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }





}
