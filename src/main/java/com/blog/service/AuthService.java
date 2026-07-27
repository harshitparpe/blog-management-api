package com.blog.service; import com.blog.dto.AuthDtos.*; public interface AuthService { AuthResponse register(RegisterRequest request); AuthResponse login(LoginRequest request); }
