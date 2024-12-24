package com.RestApiExample.demo.controllers;

import com.RestApiExample.demo.dto.LoginDto;
import com.RestApiExample.demo.dto.RegisterDto;
import com.RestApiExample.demo.models.Role;
import com.RestApiExample.demo.models.User;
import com.RestApiExample.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("Login successful!", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userService.findByUsername(registerDto.getUsername()) != null){
            return new ResponseEntity<>("Username already taken", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setEmail(registerDto.getEmail());
        user.setRole(registerDto.getRole());

        userService.createUser(user);
        return new ResponseEntity<>("Registration successful!", HttpStatus.CREATED);
    }
}
