package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.request.UpdateUserRequest;
import com.openclassrooms.mddapi.model.User;

public interface IUserService {
    User getUserByMail(String username);
    User updateUser(Long id, UpdateUserRequest request);
}
