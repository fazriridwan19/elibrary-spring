package com.lazyorchest.elibrary.services;

import com.lazyorchest.elibrary.dto.LoginRequest;
import com.lazyorchest.elibrary.dto.RegisterRequest;
import com.lazyorchest.elibrary.models.Role;
import com.lazyorchest.elibrary.models.User;
import com.lazyorchest.elibrary.repositories.UserRepository;
import com.lazyorchest.elibrary.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public User register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        return user;
    }
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return jwtService.generateToken(user);
    }
}
