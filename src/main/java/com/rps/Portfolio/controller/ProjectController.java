package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.CreateProjectRequest;
import com.rps.Portfolio.dto.ProjectDto;
import com.rps.Portfolio.dto.UpdateProjectRequest;
import com.rps.Portfolio.security.CustomUserDetails;
import com.rps.Portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    @Autowired
    private ProjectService projectService;
    
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getCurrentUserProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<ProjectDto> projects = projectService.getProjectsByUserWithTechnologies(userId);
            return ResponseEntity.ok(projects);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<ProjectDto>> getCurrentUserFeaturedProjects() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            List<ProjectDto> projects = projectService.getFeaturedProjectsByUser(userId);
            return ResponseEntity.ok(projects);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long projectId) {
        try {
            ProjectDto project = projectService.getProjectById(projectId);
            return ResponseEntity.ok(project);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@RequestBody CreateProjectRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUser().getId();
            
            try {
                ProjectDto createdProject = projectService.createProject(userId, request);
                return ResponseEntity.ok(createdProject);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long projectId, @RequestBody UpdateProjectRequest request) {
        try {
            ProjectDto updatedProject = projectService.updateProject(projectId, request);
            return ResponseEntity.ok(updatedProject);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        try {
            projectService.deleteProject(projectId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectDto>> getProjectsByUserId(@PathVariable Long userId) {
        List<ProjectDto> projects = projectService.getProjectsByUserWithTechnologies(userId);
        return ResponseEntity.ok(projects);
    }
    
    @GetMapping("/user/{userId}/featured")
    public ResponseEntity<List<ProjectDto>> getFeaturedProjectsByUserId(@PathVariable Long userId) {
        List<ProjectDto> projects = projectService.getFeaturedProjectsByUser(userId);
        return ResponseEntity.ok(projects);
    }
}