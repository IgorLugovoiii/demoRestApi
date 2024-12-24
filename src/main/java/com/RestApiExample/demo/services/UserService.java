package com.RestApiExample.demo.services;

import com.RestApiExample.demo.models.User;
import com.RestApiExample.demo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Transactional
    public void createUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void updateUser(User user) {
        userRepository.save(user);
    }
    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
