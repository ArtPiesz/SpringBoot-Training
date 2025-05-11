package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.RegisterRequestDTO;
import com.apka.quickstart.DTO.UserMapper;
import com.apka.quickstart.DTO.UserResponseDTO;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;



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

public User getUserFromContext(){
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = userRepository.findByUsername(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    return user;
}



}
