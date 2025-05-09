package com.apka.quickstart.Services;

import com.apka.quickstart.DTO.AuthenticationRequest;
import com.apka.quickstart.DTO.AuthenticationResponse;
import com.apka.quickstart.model.User;
import com.apka.quickstart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
