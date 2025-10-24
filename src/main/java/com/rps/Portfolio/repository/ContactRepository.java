package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.Contact;
import com.rps.Portfolio.entity.Contact.ContactStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    
    List<Contact> findByStatusOrderByCreatedAtDesc(ContactStatus status);
    
    List<Contact> findAllByOrderByCreatedAtDesc();
    
    List<Contact> findByEmailOrderByCreatedAtDesc(String email);
    
    @Query("SELECT c FROM Contact c WHERE c.createdAt BETWEEN :startDate AND :endDate ORDER BY c.createdAt DESC")
    List<Contact> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT c FROM Contact c WHERE c.status = 'NEW' ORDER BY c.createdAt ASC")
    List<Contact> findNewContacts();
    
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.status = :status")
    long countByStatus(@Param("status") ContactStatus status);
    
    @Query("SELECT COUNT(c) FROM Contact c WHERE c.createdAt >= :date")
    long countContactsSince(@Param("date") LocalDateTime date);
}