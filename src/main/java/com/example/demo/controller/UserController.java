package com.example.demo.controller;

import antlr.Token;
import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserEntity userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build();

            UserEntity registerUser = userService.create(userEntity);

            UserDTO resUserDTO = UserDTO.builder()
                    .email(registerUser.getEmail())
                    .id(registerUser.getId())
                    .username(registerUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(resUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }



    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity userEntity = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword(),passwordEncoder);

        if (userEntity != null) {
            final String token = tokenProvider.create(userEntity);
            final UserDTO resUserDTO = UserDTO.builder()
                    .email(userEntity.getEmail())
                    .id(userEntity.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(resUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
