package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.Skill;
import com.rps.Portfolio.entity.Skill.SkillCategory;
import com.rps.Portfolio.entity.Skill.SkillLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    
    List<Skill> findByUserIdOrderByDisplayOrderAsc(Long userId);
    
    List<Skill> findByUserIdAndCategoryOrderByDisplayOrderAsc(Long userId, SkillCategory category);
    
    List<Skill> findByUserIdAndIsFeaturedTrueOrderByDisplayOrderAsc(Long userId);
    
    List<Skill> findByUserIdAndLevelOrderByDisplayOrderAsc(Long userId, SkillLevel level);
    
    @Query("SELECT s FROM Skill s WHERE s.user.id = :userId AND s.category = :category ORDER BY s.displayOrder ASC")
    List<Skill> findSkillsByUserAndCategory(@Param("userId") Long userId, @Param("category") SkillCategory category);
    
    @Query("SELECT s FROM Skill s WHERE s.user.id = :userId AND s.isFeatured = true ORDER BY s.displayOrder ASC")
    List<Skill> findFeaturedSkillsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT s FROM Skill s WHERE s.user.id = :userId AND s.level IN ('ADVANCED', 'EXPERT') ORDER BY s.displayOrder ASC")
    List<Skill> findAdvancedSkillsByUserId(@Param("userId") Long userId);
    
    long countByUserIdAndCategory(Long userId, SkillCategory category);
}