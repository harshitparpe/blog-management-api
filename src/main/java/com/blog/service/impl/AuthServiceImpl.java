package com.blog.service.impl;

import com.blog.dto.AuthDtos.*;
import com.blog.entity.*;
import com.blog.exception.ApiExceptions.*;
import com.blog.mapper.BlogMapper;
import com.blog.repository.UserRepository;
import com.blog.security.JwtTokenProvider;
import com.blog.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager auth;
    private final JwtTokenProvider tokens;
    private final BlogMapper mapper;

    public AuthResponse register(RegisterRequest r) {

        if (users.existsByEmail(r.email())) {
            throw new EmailAlreadyExistsException(
                    "Email is already registered"
            );
        }

        if (users.existsByUsername(r.username())) {
            throw new BadRequestException(
                    "Username is already taken"
            );
        }

        var u = users.save(
                User.builder()
                        .username(r.username())
                        .email(r.email().toLowerCase())
                        .password(encoder.encode(r.password()))
                        .role(Role.USER)
                        .firstName(r.firstName())
                        .lastName(r.lastName())
                        .build()
        );

        return response(u);
    }

    public AuthResponse login(LoginRequest r) {

        try {
            auth.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            r.email(),
                            r.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(
                    "Invalid email or password"
            );
        }

        return response(
                users.findByEmail(r.email()).orElseThrow()
        );
    }

    private AuthResponse response(User u) {

        var principal =
                new org.springframework.security.core.userdetails.User(
                        u.getEmail(),
                        u.getPassword(),
                        java.util.List.of()
                );

        return new AuthResponse(
                tokens.generate(principal),
                "Bearer",
                mapper.user(u)
        );
    }
}
