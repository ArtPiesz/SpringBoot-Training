package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.LoginRequestDTO;
import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequestDTO registerRequestDTO){
        if(userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()){
            throw new RuntimeException("Email is already in use");
        }

        User user= new User(
                registerRequestDTO.getUsername(),
                registerRequestDTO.getEmail(),
                passwordEncoder.encode(registerRequestDTO.getPassword())



        );

        userRepository.save(user);
    }





}
