package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.CreateExperienceRequest;
import com.rps.Portfolio.dto.ExperienceDto;
import com.rps.Portfolio.dto.UpdateExperienceRequest;
import com.rps.Portfolio.security.CustomUserDetails;
import com.rps.Portfolio.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experience")
public class ExperienceController {
    
    @Autowired
    private ExperienceService experienceService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExperienceDto>> getCurrentUserExperiences() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<ExperienceDto> experiences = experienceService.getAllExperiencesByUser(userId);
            return ResponseEntity.ok(experiences);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExperienceDto>> getCurrentUserCurrentExperiences() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<ExperienceDto> experiences = experienceService.getCurrentExperiencesByUser(userId);
            return ResponseEntity.ok(experiences);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{experienceId}")
    public ResponseEntity<ExperienceDto> getExperienceById(@PathVariable Long experienceId) {
        try {
            ExperienceDto experience = experienceService.getExperienceById(experienceId);
            return ResponseEntity.ok(experience);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExperienceDto> createExperience(@RequestBody CreateExperienceRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            try {
                ExperienceDto createdExperience = experienceService.createExperience(userId, request);
                return ResponseEntity.ok(createdExperience);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{experienceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExperienceDto> updateExperience(@PathVariable Long experienceId, @RequestBody UpdateExperienceRequest request) {
        try {
            ExperienceDto updatedExperience = experienceService.updateExperience(experienceId, request);
            return ResponseEntity.ok(updatedExperience);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{experienceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExperience(@PathVariable Long experienceId) {
        try {
            experienceService.deleteExperience(experienceId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExperienceDto>> getExperiencesByUserId(@PathVariable Long userId) {
        List<ExperienceDto> experiences = experienceService.getAllExperiencesByUser(userId);
        return ResponseEntity.ok(experiences);
    }
    
    @GetMapping("/user/{userId}/current")
    public ResponseEntity<List<ExperienceDto>> getCurrentExperiencesByUserId(@PathVariable Long userId) {
        List<ExperienceDto> experiences = experienceService.getCurrentExperiencesByUser(userId);
        return ResponseEntity.ok(experiences);
    }
}