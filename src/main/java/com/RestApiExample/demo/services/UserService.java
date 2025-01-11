package com.RestApiExample.demo.services;

import com.RestApiExample.demo.models.User;
import com.RestApiExample.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User findByUsername(String username){
        return userRepository.findUserByUsername(username).orElse(null);
    }
}
