package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.CreateExperienceRequest;
import com.rps.Portfolio.dto.ExperienceDto;
import com.rps.Portfolio.dto.UpdateExperienceRequest;
import com.rps.Portfolio.entity.Experience;
import com.rps.Portfolio.entity.User;
import com.rps.Portfolio.repository.ExperienceRepository;
import com.rps.Portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService {
    
    @Autowired
    private ExperienceRepository experienceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<ExperienceDto> getAllExperiencesByUser(Long userId) {
        List<Experience> experiences = experienceRepository.findByUserIdOrderByStartDateDesc(userId);
        List<ExperienceDto> experienceDtos = new ArrayList<>();
        
        for (Experience experience : experiences) {
            ExperienceDto dto = convertToDto(experience);
            experienceDtos.add(dto);
        }
        
        return experienceDtos;
    }
    
    public List<ExperienceDto> getCurrentExperiencesByUser(Long userId) {
        List<Experience> experiences = experienceRepository.findByUserIdAndIsCurrentTrue(userId);
        List<ExperienceDto> experienceDtos = new ArrayList<>();
        
        for (Experience experience : experiences) {
            ExperienceDto dto = convertToDto(experience);
            experienceDtos.add(dto);
        }
        
        return experienceDtos;
    }
    
    public ExperienceDto getExperienceById(Long experienceId) {
        Optional<Experience> experienceOpt = experienceRepository.findById(experienceId);
        if (experienceOpt.isPresent()) {
            return convertToDto(experienceOpt.get());
        }
        throw new RuntimeException("Experience not found with id: " + experienceId);
    }
    
    public ExperienceDto createExperience(Long userId, CreateExperienceRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        User user = userOpt.get();
        Experience experience = new Experience();
        
        experience.setTitle(request.getTitle());
        experience.setCompany(request.getCompany());
        experience.setCompanyUrl(request.getCompanyUrl());
        experience.setLocation(request.getLocation());
        experience.setDescription(request.getDescription());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setIsCurrent(request.getIsCurrent() != null ? request.getIsCurrent() : false);
        if (request.getEmploymentType() != null) {
            experience.setEmploymentType(Experience.EmploymentType.valueOf(request.getEmploymentType().toUpperCase()));
        }
        experience.setDisplayOrder(request.getDisplayOrder());
        experience.setUser(user);
        
        Experience savedExperience = experienceRepository.save(experience);
        return convertToDto(savedExperience);
    }
    
    public ExperienceDto updateExperience(Long experienceId, UpdateExperienceRequest request) {
        Optional<Experience> experienceOpt = experienceRepository.findById(experienceId);
        if (!experienceOpt.isPresent()) {
            throw new RuntimeException("Experience not found with id: " + experienceId);
        }
        
        Experience experience = experienceOpt.get();
        
        if (request.getTitle() != null) {
            experience.setTitle(request.getTitle());
        }
        if (request.getCompany() != null) {
            experience.setCompany(request.getCompany());
        }
        if (request.getCompanyUrl() != null) {
            experience.setCompanyUrl(request.getCompanyUrl());
        }
        if (request.getLocation() != null) {
            experience.setLocation(request.getLocation());
        }
        if (request.getDescription() != null) {
            experience.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            experience.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            experience.setEndDate(request.getEndDate());
        }
        if (request.getIsCurrent() != null) {
            experience.setIsCurrent(request.getIsCurrent());
        }
        if (request.getEmploymentType() != null) {
            experience.setEmploymentType(Experience.EmploymentType.valueOf(request.getEmploymentType().toUpperCase()));
        }
        if (request.getDisplayOrder() != null) {
            experience.setDisplayOrder(request.getDisplayOrder());
        }
        
        Experience updatedExperience = experienceRepository.save(experience);
        return convertToDto(updatedExperience);
    }
    
    public void deleteExperience(Long experienceId) {
        Optional<Experience> experienceOpt = experienceRepository.findById(experienceId);
        if (!experienceOpt.isPresent()) {
            throw new RuntimeException("Experience not found with id: " + experienceId);
        }
        
        experienceRepository.deleteById(experienceId);
    }
    
    private ExperienceDto convertToDto(Experience experience) {
        ExperienceDto dto = new ExperienceDto();
        dto.setId(experience.getId());
        dto.setTitle(experience.getTitle());
        dto.setCompany(experience.getCompany());
        dto.setCompanyUrl(experience.getCompanyUrl());
        dto.setLocation(experience.getLocation());
        dto.setDescription(experience.getDescription());
        dto.setStartDate(experience.getStartDate());
        dto.setEndDate(experience.getEndDate());
        dto.setIsCurrent(experience.getIsCurrent());
        dto.setEmploymentType(experience.getEmploymentType() != null ? experience.getEmploymentType().name() : null);
        dto.setDisplayOrder(experience.getDisplayOrder());
        dto.setCreatedAt(experience.getCreatedAt());
        dto.setUpdatedAt(experience.getUpdatedAt());
        return dto;
    }
}