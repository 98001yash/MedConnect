package com.company.MedConnect.services;


import com.company.MedConnect.dtos.SignupDto;
import com.company.MedConnect.dtos.UserDto;
import com.company.MedConnect.entities.User;
import com.company.MedConnect.enums.Roles;
import com.company.MedConnect.exceptions.ResourceNotFoundException;
import com.company.MedConnect.exceptions.RuntimeConflictException;
import com.company.MedConnect.repository.UserRepository;
import com.company.MedConnect.security.JWTService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public String[] login(String email, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email,password)
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new String[]{accessToken, refreshToken};
    }

    @Transactional
    public UserDto signup(SignupDto signupDto){
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);

        if(user!=null){
            throw new RuntimeConflictException("cannot signup , User already exists with email"+signupDto.getEmail());
        }

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Roles.INDIVIDUAL));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    public String refreshToken(String refreshToken){
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found with id:"+userId));
        return jwtService.generateAccessToken(user);
    }
}
