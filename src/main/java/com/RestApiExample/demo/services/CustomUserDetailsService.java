package com.RestApiExample.demo.services;

import com.RestApiExample.demo.models.User;
import com.RestApiExample.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User with username: " + username + "was not found");
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(), Collections.singleton(grantedAuthority)
        );
    }
}
