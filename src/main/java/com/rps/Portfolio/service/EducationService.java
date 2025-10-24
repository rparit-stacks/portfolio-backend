package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.CreateEducationRequest;
import com.rps.Portfolio.dto.EducationDto;
import com.rps.Portfolio.dto.UpdateEducationRequest;
import com.rps.Portfolio.entity.Education;
import com.rps.Portfolio.entity.User;
import com.rps.Portfolio.repository.EducationRepository;
import com.rps.Portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EducationService {
    
    @Autowired
    private EducationRepository educationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<EducationDto> getAllEducationsByUser(Long userId) {
        List<Education> educations = educationRepository.findByUserIdOrderByStartDateDesc(userId);
        List<EducationDto> educationDtos = new ArrayList<>();
        
        for (Education education : educations) {
            EducationDto dto = convertToDto(education);
            educationDtos.add(dto);
        }
        
        return educationDtos;
    }
    
    public List<EducationDto> getCurrentEducationsByUser(Long userId) {
        List<Education> educations = educationRepository.findByUserIdAndIsCurrentTrue(userId);
        List<EducationDto> educationDtos = new ArrayList<>();
        
        for (Education education : educations) {
            EducationDto dto = convertToDto(education);
            educationDtos.add(dto);
        }
        
        return educationDtos;
    }
    
    public EducationDto getEducationById(Long educationId) {
        Optional<Education> educationOpt = educationRepository.findById(educationId);
        if (educationOpt.isPresent()) {
            return convertToDto(educationOpt.get());
        }
        throw new RuntimeException("Education not found with id: " + educationId);
    }
    
    public EducationDto createEducation(Long userId, CreateEducationRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        User user = userOpt.get();
        Education education = new Education();
        
        education.setDegree(request.getDegree());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setInstitution(request.getInstitution());
        education.setInstitutionUrl(request.getInstitutionUrl());
        education.setLocation(request.getLocation());
        education.setDescription(request.getDescription());
        education.setStartDate(request.getStartDate());
        education.setEndDate(request.getEndDate());
        education.setIsCurrent(request.getIsCurrent() != null ? request.getIsCurrent() : false);
        education.setGrade(request.getGrade());
        education.setDisplayOrder(request.getDisplayOrder());
        education.setUser(user);
        
        Education savedEducation = educationRepository.save(education);
        return convertToDto(savedEducation);
    }
    
    public EducationDto updateEducation(Long educationId, UpdateEducationRequest request) {
        Optional<Education> educationOpt = educationRepository.findById(educationId);
        if (!educationOpt.isPresent()) {
            throw new RuntimeException("Education not found with id: " + educationId);
        }
        
        Education education = educationOpt.get();
        
        if (request.getDegree() != null) {
            education.setDegree(request.getDegree());
        }
        if (request.getFieldOfStudy() != null) {
            education.setFieldOfStudy(request.getFieldOfStudy());
        }
        if (request.getInstitution() != null) {
            education.setInstitution(request.getInstitution());
        }
        if (request.getInstitutionUrl() != null) {
            education.setInstitutionUrl(request.getInstitutionUrl());
        }
        if (request.getLocation() != null) {
            education.setLocation(request.getLocation());
        }
        if (request.getDescription() != null) {
            education.setDescription(request.getDescription());
        }
        if (request.getStartDate() != null) {
            education.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            education.setEndDate(request.getEndDate());
        }
        if (request.getIsCurrent() != null) {
            education.setIsCurrent(request.getIsCurrent());
        }
        if (request.getGrade() != null) {
            education.setGrade(request.getGrade());
        }
        if (request.getDisplayOrder() != null) {
            education.setDisplayOrder(request.getDisplayOrder());
        }
        
        Education updatedEducation = educationRepository.save(education);
        return convertToDto(updatedEducation);
    }
    
    public void deleteEducation(Long educationId) {
        Optional<Education> educationOpt = educationRepository.findById(educationId);
        if (!educationOpt.isPresent()) {
            throw new RuntimeException("Education not found with id: " + educationId);
        }
        
        educationRepository.deleteById(educationId);
    }
    
    private EducationDto convertToDto(Education education) {
        EducationDto dto = new EducationDto();
        dto.setId(education.getId());
        dto.setDegree(education.getDegree());
        dto.setFieldOfStudy(education.getFieldOfStudy());
        dto.setInstitution(education.getInstitution());
        dto.setInstitutionUrl(education.getInstitutionUrl());
        dto.setLocation(education.getLocation());
        dto.setDescription(education.getDescription());
        dto.setStartDate(education.getStartDate());
        dto.setEndDate(education.getEndDate());
        dto.setIsCurrent(education.getIsCurrent());
        dto.setGrade(education.getGrade());
        dto.setDisplayOrder(education.getDisplayOrder());
        dto.setCreatedAt(education.getCreatedAt());
        dto.setUpdatedAt(education.getUpdatedAt());
        return dto;
    }
}