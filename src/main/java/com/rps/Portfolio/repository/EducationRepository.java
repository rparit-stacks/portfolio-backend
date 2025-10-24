package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    
    List<Education> findByUserIdOrderByStartDateDesc(Long userId);
    
    List<Education> findByUserIdOrderByDisplayOrderAsc(Long userId);
    
    List<Education> findByUserIdAndIsCurrentTrue(Long userId);
    
    @Query("SELECT e FROM Education e WHERE e.user.id = :userId ORDER BY e.startDate DESC")
    List<Education> findByUserIdOrderByStartDateDescending(@Param("userId") Long userId);
    
    @Query("SELECT e FROM Education e WHERE e.user.id = :userId AND e.isCurrent = true")
    List<Education> findCurrentEducationByUserId(@Param("userId") Long userId);
    
    List<Education> findByInstitutionContainingIgnoreCase(String institution);
    
    List<Education> findByDegreeContainingIgnoreCase(String degree);
}