package com.rps.Portfolio.service;

import com.rps.Portfolio.dto.AnalyticsDto;
import com.rps.Portfolio.dto.AnalyticsSummaryDto;
import com.rps.Portfolio.dto.CreateAnalyticsRequest;
import com.rps.Portfolio.entity.Analytics;
import com.rps.Portfolio.repository.AnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AnalyticsService {
    
    @Autowired
    private AnalyticsRepository analyticsRepository;
    
    public AnalyticsDto trackVisit(CreateAnalyticsRequest request) {
        Analytics analytics = new Analytics();
        
        analytics.setVisitorIp(request.getVisitorIp());
        analytics.setUserAgent(request.getUserAgent());
        analytics.setPageUrl(request.getPageUrl());
        analytics.setPageTitle(request.getPageTitle());
        analytics.setReferrerUrl(request.getReferrerUrl());
        analytics.setSessionId(request.getSessionId());
        analytics.setCountry(request.getCountry());
        analytics.setCity(request.getCity());
        analytics.setDeviceType(request.getDeviceType());
        analytics.setBrowser(request.getBrowser());
        analytics.setOperatingSystem(request.getOperatingSystem());
        analytics.setVisitDuration(request.getVisitDuration());
        if (request.getVisitType() != null) {
            analytics.setVisitType(Analytics.VisitType.valueOf(request.getVisitType().toUpperCase()));
        }
        
        Analytics savedAnalytics = analyticsRepository.save(analytics);
        return convertToDto(savedAnalytics);
    }
    
    public AnalyticsSummaryDto getAnalyticsSummary() {
        AnalyticsSummaryDto summary = new AnalyticsSummaryDto();
        
        summary.setTotalVisitors(analyticsRepository.getTotalVisitorCount());
        summary.setUniqueVisitors(analyticsRepository.getUniqueVisitorCount());
        
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime weekAgo = today.minusDays(7);
        LocalDateTime monthAgo = today.minusDays(30);
        
        summary.setTodayVisitors(analyticsRepository.getVisitorCountSince(today));
        summary.setThisWeekVisitors(analyticsRepository.getVisitorCountSince(weekAgo));
        summary.setThisMonthVisitors(analyticsRepository.getVisitorCountSince(monthAgo));
        
        summary.setProjectViews(analyticsRepository.getCountByVisitType(Analytics.VisitType.PROJECT_VIEW));
        summary.setContactFormSubmissions(analyticsRepository.getCountByVisitType(Analytics.VisitType.CONTACT_FORM));
        summary.setResumeDownloads(analyticsRepository.getCountByVisitType(Analytics.VisitType.DOWNLOAD_RESUME));
        
        summary.setVisitorsByCountry(convertToMap(analyticsRepository.getVisitorsByCountry()));
        summary.setVisitorsByDevice(convertToMap(analyticsRepository.getVisitorsByDevice()));
        summary.setVisitorsByBrowser(convertToMap(analyticsRepository.getVisitorsByBrowser()));
        summary.setMostVisitedPages(convertToMap(analyticsRepository.getMostVisitedPages()));
        
        return summary;
    }
    
    public List<AnalyticsDto> getRecentVisits(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<Analytics> analytics = analyticsRepository.getRecentVisits(since);
        List<AnalyticsDto> analyticsDtos = new ArrayList<>();
        
        for (Analytics analytic : analytics) {
            AnalyticsDto dto = convertToDto(analytic);
            analyticsDtos.add(dto);
        }
        
        return analyticsDtos;
    }
    
    public List<AnalyticsDto> getSessionVisits(String sessionId) {
        List<Analytics> analytics = analyticsRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);
        List<AnalyticsDto> analyticsDtos = new ArrayList<>();
        
        for (Analytics analytic : analytics) {
            AnalyticsDto dto = convertToDto(analytic);
            analyticsDtos.add(dto);
        }
        
        return analyticsDtos;
    }
    
    public AnalyticsDto getAnalyticsById(Long analyticsId) {
        Optional<Analytics> analyticsOpt = analyticsRepository.findById(analyticsId);
        if (analyticsOpt.isPresent()) {
            return convertToDto(analyticsOpt.get());
        }
        throw new RuntimeException("Analytics not found with id: " + analyticsId);
    }
    
    public long getTotalVisitorCount() {
        return analyticsRepository.getTotalVisitorCount();
    }
    
    public long getUniqueVisitorCount() {
        return analyticsRepository.getUniqueVisitorCount();
    }
    
    private Map<String, Long> convertToMap(List<Object[]> results) {
        Map<String, Long> map = new HashMap<>();
        
        for (Object[] result : results) {
            String key = (String) result[0];
            Long count = (Long) result[1];
            if (key != null) {
                map.put(key, count);
            }
        }
        
        return map;
    }
    
    private AnalyticsDto convertToDto(Analytics analytics) {
        AnalyticsDto dto = new AnalyticsDto();
        dto.setId(analytics.getId());
        dto.setVisitorIp(analytics.getVisitorIp());
        dto.setUserAgent(analytics.getUserAgent());
        dto.setPageUrl(analytics.getPageUrl());
        dto.setPageTitle(analytics.getPageTitle());
        dto.setReferrerUrl(analytics.getReferrerUrl());
        dto.setSessionId(analytics.getSessionId());
        dto.setCountry(analytics.getCountry());
        dto.setCity(analytics.getCity());
        dto.setDeviceType(analytics.getDeviceType());
        dto.setBrowser(analytics.getBrowser());
        dto.setOperatingSystem(analytics.getOperatingSystem());
        dto.setVisitDuration(analytics.getVisitDuration());
        dto.setVisitType(analytics.getVisitType() != null ? analytics.getVisitType().name() : null);
        dto.setCreatedAt(analytics.getCreatedAt());
        return dto;
    }
}