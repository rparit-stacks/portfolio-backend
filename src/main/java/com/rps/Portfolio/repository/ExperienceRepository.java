package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.Experience;
import com.rps.Portfolio.entity.Experience.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    
    List<Experience> findByUserIdOrderByStartDateDesc(Long userId);
    
    List<Experience> findByUserIdOrderByDisplayOrderAsc(Long userId);
    
    List<Experience> findByUserIdAndIsCurrentTrue(Long userId);
    
    List<Experience> findByUserIdAndEmploymentType(Long userId, EmploymentType employmentType);
    
    @Query("SELECT e FROM Experience e WHERE e.user.id = :userId AND e.isCurrent = true ORDER BY e.startDate DESC")
    List<Experience> findCurrentExperiencesByUserId(@Param("userId") Long userId);
    
    @Query("SELECT e FROM Experience e WHERE e.user.id = :userId ORDER BY e.startDate DESC")
    List<Experience> findByUserIdOrderByStartDateDescending(@Param("userId") Long userId);
    
    long countByUserIdAndIsCurrent(Long userId, Boolean isCurrent);
}