package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.CreateSkillRequest;
import com.rps.Portfolio.dto.SkillDto;
import com.rps.Portfolio.dto.UpdateSkillRequest;
import com.rps.Portfolio.entity.Skill;
import com.rps.Portfolio.entity.User;
import com.rps.Portfolio.repository.SkillRepository;
import com.rps.Portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<SkillDto> getAllSkillsByUser(Long userId) {
        List<Skill> skills = skillRepository.findByUserIdOrderByDisplayOrderAsc(userId);
        List<SkillDto> skillDtos = new ArrayList<>();
        
        for (Skill skill : skills) {
            SkillDto dto = convertToDto(skill);
            skillDtos.add(dto);
        }
        
        return skillDtos;
    }
    
    public List<SkillDto> getFeaturedSkillsByUser(Long userId) {
        List<Skill> skills = skillRepository.findByUserIdAndIsFeaturedTrueOrderByDisplayOrderAsc(userId);
        List<SkillDto> skillDtos = new ArrayList<>();
        
        for (Skill skill : skills) {
            SkillDto dto = convertToDto(skill);
            skillDtos.add(dto);
        }
        
        return skillDtos;
    }
    
    public List<SkillDto> getSkillsByCategory(Long userId, String category) {
        Skill.SkillCategory skillCategory = Skill.SkillCategory.valueOf(category.toUpperCase());
        List<Skill> skills = skillRepository.findByUserIdAndCategoryOrderByDisplayOrderAsc(userId, skillCategory);
        List<SkillDto> skillDtos = new ArrayList<>();
        
        for (Skill skill : skills) {
            SkillDto dto = convertToDto(skill);
            skillDtos.add(dto);
        }
        
        return skillDtos;
    }
    
    public SkillDto getSkillById(Long skillId) {
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (skillOpt.isPresent()) {
            return convertToDto(skillOpt.get());
        }
        throw new RuntimeException("Skill not found with id: " + skillId);
    }
    
    public SkillDto createSkill(Long userId, CreateSkillRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        User user = userOpt.get();
        Skill skill = new Skill();
        
        skill.setName(request.getName());
        if (request.getCategory() != null) {
            skill.setCategory(Skill.SkillCategory.valueOf(request.getCategory().toUpperCase()));
        }
        if (request.getLevel() != null) {
            skill.setLevel(Skill.SkillLevel.valueOf(request.getLevel().toUpperCase()));
        }
        skill.setYearsOfExperience(request.getYearsOfExperience());
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        skill.setIconUrl(request.getIconUrl());
        skill.setUser(user);
        
        Skill savedSkill = skillRepository.save(skill);
        return convertToDto(savedSkill);
    }
    
    public SkillDto updateSkill(Long skillId, UpdateSkillRequest request) {
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (!skillOpt.isPresent()) {
            throw new RuntimeException("Skill not found with id: " + skillId);
        }
        
        Skill skill = skillOpt.get();
        
        if (request.getName() != null) {
            skill.setName(request.getName());
        }
        if (request.getCategory() != null) {
            skill.setCategory(Skill.SkillCategory.valueOf(request.getCategory().toUpperCase()));
        }
        if (request.getLevel() != null) {
            skill.setLevel(Skill.SkillLevel.valueOf(request.getLevel().toUpperCase()));
        }
        if (request.getYearsOfExperience() != null) {
            skill.setYearsOfExperience(request.getYearsOfExperience());
        }
        if (request.getDisplayOrder() != null) {
            skill.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getIsFeatured() != null) {
            skill.setIsFeatured(request.getIsFeatured());
        }
        if (request.getIconUrl() != null) {
            skill.setIconUrl(request.getIconUrl());
        }
        
        Skill updatedSkill = skillRepository.save(skill);
        return convertToDto(updatedSkill);
    }
    
    public void deleteSkill(Long skillId) {
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        if (!skillOpt.isPresent()) {
            throw new RuntimeException("Skill not found with id: " + skillId);
        }
        
        skillRepository.deleteById(skillId);
    }
    
    private SkillDto convertToDto(Skill skill) {
        SkillDto dto = new SkillDto();
        dto.setId(skill.getId());
        dto.setName(skill.getName());
        dto.setCategory(skill.getCategory() != null ? skill.getCategory().name() : null);
        dto.setLevel(skill.getLevel() != null ? skill.getLevel().name() : null);
        dto.setYearsOfExperience(skill.getYearsOfExperience());
        dto.setDisplayOrder(skill.getDisplayOrder());
        dto.setIsFeatured(skill.getIsFeatured());
        dto.setIconUrl(skill.getIconUrl());
        dto.setCreatedAt(skill.getCreatedAt());
        dto.setUpdatedAt(skill.getUpdatedAt());
        return dto;
    }
}