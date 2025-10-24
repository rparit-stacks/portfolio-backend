package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.CreateProjectRequest;
import com.rps.Portfolio.dto.ProjectDto;
import com.rps.Portfolio.dto.UpdateProjectRequest;
import com.rps.Portfolio.entity.Project;
import com.rps.Portfolio.entity.ProjectTechnology;
import com.rps.Portfolio.entity.User;
import com.rps.Portfolio.repository.ProjectRepository;
import com.rps.Portfolio.repository.ProjectTechnologyRepository;
import com.rps.Portfolio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ProjectService {
    
    @Autowired
    private ProjectRepository projectRepository;
    
    @Autowired
    private ProjectTechnologyRepository projectTechnologyRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<ProjectDto> getAllProjectsByUser(Long userId) {
        List<Project> projects = projectRepository.findByUserIdOrderByDisplayOrderAsc(userId);
        List<ProjectDto> projectDtos = new ArrayList<>();
        
        for (Project project : projects) {
            ProjectDto dto = convertToDto(project);
            projectDtos.add(dto);
        }
        
        return projectDtos;
    }
    
    public List<ProjectDto> getFeaturedProjectsByUser(Long userId) {
        List<Project> projects = projectRepository.findByUserIdAndIsFeaturedTrueOrderByDisplayOrderAsc(userId);
        List<ProjectDto> projectDtos = new ArrayList<>();
        
        for (Project project : projects) {
            ProjectDto dto = convertToDto(project);
            projectDtos.add(dto);
        }
        
        return projectDtos;
    }
    
    public List<ProjectDto> getProjectsByUserWithTechnologies(Long userId) {
        List<Project> projects = projectRepository.findByUserIdWithTechnologies(userId);
        List<ProjectDto> projectDtos = new ArrayList<>();
        
        for (Project project : projects) {
            ProjectDto dto = convertToDtoWithTechnologies(project);
            projectDtos.add(dto);
        }
        
        return projectDtos;
    }
    
    public ProjectDto getProjectById(Long projectId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (projectOpt.isPresent()) {
            return convertToDtoWithTechnologies(projectOpt.get());
        }
        throw new RuntimeException("Project not found with id: " + projectId);
    }
    
    @Transactional
    public ProjectDto createProject(Long userId, CreateProjectRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        
        User user = userOpt.get();
        Project project = new Project();
        
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setShortDescription(request.getShortDescription());
        project.setProjectUrl(request.getProjectUrl());
        project.setGithubUrl(request.getGithubUrl());
        project.setDemoUrl(request.getDemoUrl());
        if (request.getDemoType() != null) {
            project.setDemoType(Project.DemoType.valueOf(request.getDemoType().toUpperCase()));
        }
        project.setImageUrl(request.getImageUrl());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setIsFeatured(request.getIsFeatured() != null ? request.getIsFeatured() : false);
        project.setDisplayOrder(request.getDisplayOrder());
        if (request.getStatus() != null) {
            project.setStatus(Project.ProjectStatus.valueOf(request.getStatus().toUpperCase()));
        }
        project.setUser(user);
        
        Project savedProject = projectRepository.save(project);
        
        if (request.getTechnologies() != null && !request.getTechnologies().isEmpty()) {
            saveTechnologies(savedProject, request.getTechnologies());
        }
        
        return convertToDtoWithTechnologies(savedProject);
    }
    
    @Transactional
    public ProjectDto updateProject(Long projectId, UpdateProjectRequest request) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (!projectOpt.isPresent()) {
            throw new RuntimeException("Project not found with id: " + projectId);
        }
        
        Project project = projectOpt.get();
        
        if (request.getTitle() != null) {
            project.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            project.setDescription(request.getDescription());
        }
        if (request.getShortDescription() != null) {
            project.setShortDescription(request.getShortDescription());
        }
        if (request.getProjectUrl() != null) {
            project.setProjectUrl(request.getProjectUrl());
        }
        if (request.getGithubUrl() != null) {
            project.setGithubUrl(request.getGithubUrl());
        }
        if (request.getDemoUrl() != null) {
            project.setDemoUrl(request.getDemoUrl());
        }
        if (request.getDemoType() != null) {
            project.setDemoType(Project.DemoType.valueOf(request.getDemoType().toUpperCase()));
        }
        if (request.getImageUrl() != null) {
            project.setImageUrl(request.getImageUrl());
        }
        if (request.getStartDate() != null) {
            project.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            project.setEndDate(request.getEndDate());
        }
        if (request.getIsFeatured() != null) {
            project.setIsFeatured(request.getIsFeatured());
        }
        if (request.getDisplayOrder() != null) {
            project.setDisplayOrder(request.getDisplayOrder());
        }
        if (request.getStatus() != null) {
            project.setStatus(Project.ProjectStatus.valueOf(request.getStatus().toUpperCase()));
        }
        
        Project updatedProject = projectRepository.save(project);
        
        if (request.getTechnologies() != null) {
            projectTechnologyRepository.deleteByProjectId(projectId);
            if (!request.getTechnologies().isEmpty()) {
                saveTechnologies(updatedProject, request.getTechnologies());
            }
        }
        
        return convertToDtoWithTechnologies(updatedProject);
    }
    
    @Transactional
    public void deleteProject(Long projectId) {
        Optional<Project> projectOpt = projectRepository.findById(projectId);
        if (!projectOpt.isPresent()) {
            throw new RuntimeException("Project not found with id: " + projectId);
        }
        
        projectTechnologyRepository.deleteByProjectId(projectId);
        projectRepository.deleteById(projectId);
    }
    
    public long getProjectCountByUser(Long userId) {
        return projectRepository.countByUserIdAndStatus(userId, Project.ProjectStatus.COMPLETED);
    }
    
    private void saveTechnologies(Project project, List<String> technologyNames) {
        for (String techName : technologyNames) {
            ProjectTechnology tech = new ProjectTechnology();
            tech.setName(techName);
            tech.setProject(project);
            projectTechnologyRepository.save(tech);
        }
    }
    
    private ProjectDto convertToDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setTitle(project.getTitle());
        dto.setDescription(project.getDescription());
        dto.setShortDescription(project.getShortDescription());
        dto.setProjectUrl(project.getProjectUrl());
        dto.setGithubUrl(project.getGithubUrl());
        dto.setDemoUrl(project.getDemoUrl());
        dto.setDemoType(project.getDemoType() != null ? project.getDemoType().name() : null);
        dto.setImageUrl(project.getImageUrl());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setIsFeatured(project.getIsFeatured());
        dto.setDisplayOrder(project.getDisplayOrder());
        dto.setStatus(project.getStatus() != null ? project.getStatus().name() : null);
        dto.setCreatedAt(project.getCreatedAt());
        dto.setUpdatedAt(project.getUpdatedAt());
        return dto;
    }
    
    private ProjectDto convertToDtoWithTechnologies(Project project) {
        ProjectDto dto = convertToDto(project);
        
        List<String> techNames = new ArrayList<>();
        if (project.getTechnologies() != null) {
            for (ProjectTechnology tech : project.getTechnologies()) {
                techNames.add(tech.getName());
            }
        }
        dto.setTechnologies(techNames);
        
        return dto;
    }
}