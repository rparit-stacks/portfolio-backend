package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects LEFT JOIN FETCH u.experiences LEFT JOIN FETCH u.educations LEFT JOIN FETCH u.skills WHERE u.id = :id")
    Optional<User> findByIdWithDetails(Long id);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.projects p WHERE u.id = :id AND p.isFeatured = true")
    Optional<User> findByIdWithFeaturedProjects(Long id);

    User findUserById(Long id);
}