package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.request.UpdateUserRequest;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User getUserByMail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
    }
    public User getUserByMailorUserName(String userName) {
    return userRepository.findByEmailOrUsername(userName)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public User updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur non trouvé"));

        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        if(!request.getPassword().isEmpty() ){user.setPassword(passwordEncoder.encode(request.getPassword())); }
     

        return userRepository.save(user);
    }
}
