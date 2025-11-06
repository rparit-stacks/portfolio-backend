package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.UpdateUserProfileRequest;
import com.rps.Portfolio.dto.UserDto;
import com.rps.Portfolio.entity.User;
import com.rps.Portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public UserDto getUserProfile(Long userId) {
        User user = userRepository.findUserById(userId);
        if (user != null) {
            return convertToDto(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }
    
    public UserDto getUserProfileWithDetails(Long userId) {
        System.out.println("called 2");
        User user = userRepository.findUserById(userId);
        if (user != null) {
            return convertToDtoWithDetails(user);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }
    
    public UserDto updateUserProfile(Long userId, UpdateUserProfileRequest request) {
        User user = userRepository.findUserById(userId);
        if (user != null) {
            
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLastName(request.getLastName());
            }
            if (request.getTitle() != null) {
                user.setTitle(request.getTitle());
            }
            if (request.getBio() != null) {
                user.setBio(request.getBio());
            }
            if (request.getProfileImage() != null) {
                user.setProfileImage(request.getProfileImage());
            }
            if (request.getPhone() != null) {
                user.setPhone(request.getPhone());
            }
            if (request.getLocation() != null) {
                user.setLocation(request.getLocation());
            }
            if (request.getLinkedinUrl() != null) {
                user.setLinkedinUrl(request.getLinkedinUrl());
            }
            if (request.getGithubUrl() != null) {
                user.setGithubUrl(request.getGithubUrl());
            }
            if (request.getWebsiteUrl() != null) {
                user.setWebsiteUrl(request.getWebsiteUrl());
            }
            
            User updatedUser = userRepository.save(user);
            return convertToDto(updatedUser);
        }
        throw new RuntimeException("User not found with id: " + userId);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
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
        dto.setRole(user.getRole() != null ? user.getRole().name() : null);
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
    
    private UserDto convertToDtoWithDetails(User user) {
        UserDto dto = convertToDto(user);
        return dto;
    }
}