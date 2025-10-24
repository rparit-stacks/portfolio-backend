package com.rps.Portfolio.repository;

import com.rps.Portfolio.entity.Analytics;
import com.rps.Portfolio.entity.Analytics.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AnalyticsRepository extends JpaRepository<Analytics, Long> {
    
    @Query("SELECT COUNT(a) FROM Analytics a")
    long getTotalVisitorCount();
    
    @Query("SELECT COUNT(DISTINCT a.visitorIp) FROM Analytics a")
    long getUniqueVisitorCount();
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.createdAt >= :date")
    long getVisitorCountSince(@Param("date") LocalDateTime date);
    
    @Query("SELECT COUNT(DISTINCT a.visitorIp) FROM Analytics a WHERE a.createdAt >= :date")
    long getUniqueVisitorCountSince(@Param("date") LocalDateTime date);
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.createdAt BETWEEN :startDate AND :endDate")
    long getVisitorCountBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a.country, COUNT(a) FROM Analytics a GROUP BY a.country ORDER BY COUNT(a) DESC")
    List<Object[]> getVisitorsByCountry();
    
    @Query("SELECT a.deviceType, COUNT(a) FROM Analytics a GROUP BY a.deviceType ORDER BY COUNT(a) DESC")
    List<Object[]> getVisitorsByDevice();
    
    @Query("SELECT a.browser, COUNT(a) FROM Analytics a GROUP BY a.browser ORDER BY COUNT(a) DESC")
    List<Object[]> getVisitorsByBrowser();
    
    @Query("SELECT a.pageUrl, COUNT(a) FROM Analytics a GROUP BY a.pageUrl ORDER BY COUNT(a) DESC")
    List<Object[]> getMostVisitedPages();
    
    @Query("SELECT COUNT(a) FROM Analytics a WHERE a.visitType = :visitType")
    long getCountByVisitType(@Param("visitType") VisitType visitType);
    
    @Query("SELECT a FROM Analytics a WHERE a.createdAt >= :date ORDER BY a.createdAt DESC")
    List<Analytics> getRecentVisits(@Param("date") LocalDateTime date);
    
    List<Analytics> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}