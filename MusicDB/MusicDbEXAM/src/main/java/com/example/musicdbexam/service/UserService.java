package com.example.musicdbexam.service;

import com.example.musicdbexam.model.entity.User;
import com.example.musicdbexam.model.service.UserServiceModel;

public interface UserService {
    void registerUser(UserServiceModel userServiceModel);



    UserServiceModel findByUsernameAndPassword(String username, String password);

    void loginUser(Long id, String username);

    boolean existingUsernameOrEmail(String username, String email);

    User findById(Long id);
}
