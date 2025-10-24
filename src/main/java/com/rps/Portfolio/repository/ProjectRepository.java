package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.Project;
import com.rps.Portfolio.entity.Project.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    
    List<Project> findByUserIdOrderByDisplayOrderAsc(Long userId);
    
    List<Project> findByUserIdAndIsFeaturedTrueOrderByDisplayOrderAsc(Long userId);
    
    List<Project> findByUserIdAndStatusOrderByDisplayOrderAsc(Long userId, ProjectStatus status);
    
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.technologies WHERE p.user.id = :userId ORDER BY p.displayOrder ASC")
    List<Project> findByUserIdWithTechnologies(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Project p WHERE p.user.id = :userId AND p.isFeatured = true ORDER BY p.displayOrder ASC")
    List<Project> findFeaturedProjectsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Project p WHERE p.user.id = :userId AND p.status = 'COMPLETED' ORDER BY p.endDate DESC")
    List<Project> findCompletedProjectsByUserId(@Param("userId") Long userId);
    
    long countByUserIdAndStatus(Long userId, ProjectStatus status);
}