package com.rps.Portfolio.controller;

import com.rps.Portfolio.dto.AnalyticsDto;
import com.rps.Portfolio.dto.AnalyticsSummaryDto;
import com.rps.Portfolio.dto.CreateAnalyticsRequest;
import com.rps.Portfolio.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    @PostMapping("/track")
    public ResponseEntity<AnalyticsDto> trackVisit(@RequestBody CreateAnalyticsRequest request) {
        try {
            AnalyticsDto analytics = analyticsService.trackVisit(request);
            return ResponseEntity.ok(analytics);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsSummaryDto> getAnalyticsSummary() {
        AnalyticsSummaryDto summary = analyticsService.getAnalyticsSummary();
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/recent/{days}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AnalyticsDto>> getRecentVisits(@PathVariable int days) {
        List<AnalyticsDto> visits = analyticsService.getRecentVisits(days);
        return ResponseEntity.ok(visits);
    }
    
    @GetMapping("/session/{sessionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AnalyticsDto>> getSessionVisits(@PathVariable String sessionId) {
        List<AnalyticsDto> visits = analyticsService.getSessionVisits(sessionId);
        return ResponseEntity.ok(visits);
    }
    
    @GetMapping("/{analyticsId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnalyticsDto> getAnalyticsById(@PathVariable Long analyticsId) {
        try {
            AnalyticsDto analytics = analyticsService.getAnalyticsById(analyticsId);
            return ResponseEntity.ok(analytics);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/count/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalVisitorCount() {
        long count = analyticsService.getTotalVisitorCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/count/unique")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUniqueVisitorCount() {
        long count = analyticsService.getUniqueVisitorCount();
        return ResponseEntity.ok(count);
    }
}