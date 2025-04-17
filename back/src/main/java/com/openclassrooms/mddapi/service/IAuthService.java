package com.openclassrooms.mddapi.service;
import com.openclassrooms.mddapi.dto.request.LoginRequest;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    void register(RegisterRequest request);
    String login(LoginRequest request);
}
