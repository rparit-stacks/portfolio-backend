package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.AuthResponse;
import com.rps.Portfolio.dto.LoginRequest;
import com.rps.Portfolio.dto.RegisterRequest;
import com.rps.Portfolio.dto.UserDto;
import com.rps.Portfolio.entity.User;
import com.rps.Portfolio.repository.UserRepository;
import com.rps.Portfolio.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Save authentication in session
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, 
                                SecurityContextHolder.getContext());
            
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            
            UserDto userDto = convertToDto(user);
            
            return ResponseEntity.ok(new AuthResponse("Login successful", true, userDto));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse("Invalid email or password", false));
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (userRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest()
                    .body(new AuthResponse("Email already exists", false));
            }
            
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());
            
            if (registerRequest.getRole() != null) {
                user.setRole(User.Role.valueOf(registerRequest.getRole().toUpperCase()));
            } else {
                user.setRole(User.Role.VISITOR);
            }
            
            User savedUser = userRepository.save(user);
            UserDto userDto = convertToDto(savedUser);
            
            return ResponseEntity.ok(new AuthResponse("Registration successful", true, userDto));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new AuthResponse("Registration failed: " + e.getMessage(), false));
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(new AuthResponse("Logout successful", true));
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            User user = userDetails.getUser();
            UserDto userDto = convertToDto(user);
            return ResponseEntity.ok(userDto);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setTitle(user.getTitle());
        dto.setBio(user.getBio());
        dto.setProfileImage(user.getProfileImage());
        dto.setPhone(user.getPhone());
        dto.setLocation(user.getLocation());
        dto.setLinkedinUrl(user.getLinkedinUrl());
        dto.setGithubUrl(user.getGithubUrl());
        dto.setWebsiteUrl(user.getWebsiteUrl());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}