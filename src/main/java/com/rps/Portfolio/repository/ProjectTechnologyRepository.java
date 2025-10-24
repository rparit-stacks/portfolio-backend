package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.ProjectTechnology;
import com.rps.Portfolio.entity.ProjectTechnology.TechnologyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectTechnologyRepository extends JpaRepository<ProjectTechnology, Long> {
    
    List<ProjectTechnology> findByProjectId(Long projectId);
    
    List<ProjectTechnology> findByProjectIdAndType(Long projectId, TechnologyType type);
    
    @Query("SELECT pt FROM ProjectTechnology pt WHERE pt.project.user.id = :userId")
    List<ProjectTechnology> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT DISTINCT pt.name FROM ProjectTechnology pt WHERE pt.project.user.id = :userId")
    List<String> findDistinctTechnologyNamesByUserId(@Param("userId") Long userId);
    
    @Query("SELECT pt FROM ProjectTechnology pt WHERE pt.project.user.id = :userId AND pt.type = :type")
    List<ProjectTechnology> findByUserIdAndType(@Param("userId") Long userId, @Param("type") TechnologyType type);
    
    void deleteByProjectId(Long projectId);
}