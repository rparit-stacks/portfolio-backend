package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.CreateEducationRequest;
import com.rps.Portfolio.dto.EducationDto;
import com.rps.Portfolio.dto.UpdateEducationRequest;
import com.rps.Portfolio.security.CustomUserDetails;
import com.rps.Portfolio.service.EducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/education")
public class EducationController {
    
    @Autowired
    private EducationService educationService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EducationDto>> getCurrentUserEducations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<EducationDto> educations = educationService.getAllEducationsByUser(userId);
            return ResponseEntity.ok(educations);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EducationDto>> getCurrentUserCurrentEducations() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<EducationDto> educations = educationService.getCurrentEducationsByUser(userId);
            return ResponseEntity.ok(educations);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{educationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EducationDto> getEducationById(@PathVariable Long educationId) {
        try {
            EducationDto education = educationService.getEducationById(educationId);
            return ResponseEntity.ok(education);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EducationDto> createEducation(@RequestBody CreateEducationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            try {
                EducationDto createdEducation = educationService.createEducation(userId, request);
                return ResponseEntity.ok(createdEducation);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{educationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EducationDto> updateEducation(@PathVariable Long educationId, @RequestBody UpdateEducationRequest request) {
        try {
            EducationDto updatedEducation = educationService.updateEducation(educationId, request);
            return ResponseEntity.ok(updatedEducation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{educationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long educationId) {
        try {
            educationService.deleteEducation(educationId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EducationDto>> getEducationsByUserId(@PathVariable Long userId) {
        List<EducationDto> educations = educationService.getAllEducationsByUser(userId);
        return ResponseEntity.ok(educations);
    }
    
    @GetMapping("/user/{userId}/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EducationDto>> getCurrentEducationsByUserId(@PathVariable Long userId) {
        List<EducationDto> educations = educationService.getCurrentEducationsByUser(userId);
        return ResponseEntity.ok(educations);
    }
}