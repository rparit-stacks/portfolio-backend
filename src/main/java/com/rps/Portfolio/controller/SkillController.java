package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.CreateSkillRequest;
import com.rps.Portfolio.dto.SkillDto;
import com.rps.Portfolio.dto.UpdateSkillRequest;
import com.rps.Portfolio.security.CustomUserDetails;
import com.rps.Portfolio.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    
    @Autowired
    private SkillService skillService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getCurrentUserSkills() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<SkillDto> skills = skillService.getAllSkillsByUser(userId);
            return ResponseEntity.ok(skills);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/featured")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getCurrentUserFeaturedSkills() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<SkillDto> skills = skillService.getFeaturedSkillsByUser(userId);
            return ResponseEntity.ok(skills);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getCurrentUserSkillsByCategory(@PathVariable String category) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            try {
                List<SkillDto> skills = skillService.getSkillsByCategory(userId, category);
                return ResponseEntity.ok(skills);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{skillId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillDto> getSkillById(@PathVariable Long skillId) {
        try {
            SkillDto skill = skillService.getSkillById(skillId);
            return ResponseEntity.ok(skill);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillDto> createSkill(@RequestBody CreateSkillRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            try {
                SkillDto createdSkill = skillService.createSkill(userId, request);
                return ResponseEntity.ok(createdSkill);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{skillId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SkillDto> updateSkill(@PathVariable Long skillId, @RequestBody UpdateSkillRequest request) {
        try {
            SkillDto updatedSkill = skillService.updateSkill(skillId, request);
            return ResponseEntity.ok(updatedSkill);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{skillId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long skillId) {
        try {
            skillService.deleteSkill(skillId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getSkillsByUserId(@PathVariable Long userId) {
        List<SkillDto> skills = skillService.getAllSkillsByUser(userId);
        return ResponseEntity.ok(skills);
    }
    
    @GetMapping("/user/{userId}/featured")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getFeaturedSkillsByUserId(@PathVariable Long userId) {
        List<SkillDto> skills = skillService.getFeaturedSkillsByUser(userId);
        return ResponseEntity.ok(skills);
    }
    
    @GetMapping("/user/{userId}/category/{category}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDto>> getSkillsByUserIdAndCategory(@PathVariable Long userId, @PathVariable String category) {
        try {
            List<SkillDto> skills = skillService.getSkillsByCategory(userId, category);
            return ResponseEntity.ok(skills);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}