package com.example.musicdbexam.service.impl;

import com.example.musicdbexam.model.entity.User;
import com.example.musicdbexam.model.service.UserServiceModel;
import com.example.musicdbexam.repository.UserRepository;
import com.example.musicdbexam.service.UserService;
import com.example.musicdbexam.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.currentUser = currentUser;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);

        userRepository.save(user);
    }

    @Override
    public UserServiceModel findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public void loginUser(Long id, String username) {
        currentUser.setId(id);
        currentUser.setUsername(username);
    }

    @Override
    public boolean existingUsernameOrEmail(String username, String email) {
        return userRepository.existsByUsernameOrEmail(username,email);
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id).orElse(null);
    }


}
