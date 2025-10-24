package com.rps.Portfolio.dto;

import java.util.Map;

public class AnalyticsSummaryDto {
    
    private long totalVisitors;
    private long uniqueVisitors;
    private long todayVisitors;
    private long thisWeekVisitors;
    private long thisMonthVisitors;
    private long projectViews;
    private long contactFormSubmissions;
    private long resumeDownloads;
    private Map<String, Long> visitorsByCountry;
    private Map<String, Long> visitorsByDevice;
    private Map<String, Long> visitorsByBrowser;
    private Map<String, Long> mostVisitedPages;

    public long getTotalVisitors() {
        return totalVisitors;
    }

    public void setTotalVisitors(long totalVisitors) {
        this.totalVisitors = totalVisitors;
    }

    public long getUniqueVisitors() {
        return uniqueVisitors;
    }

    public void setUniqueVisitors(long uniqueVisitors) {
        this.uniqueVisitors = uniqueVisitors;
    }

    public long getTodayVisitors() {
        return todayVisitors;
    }

    public void setTodayVisitors(long todayVisitors) {
        this.todayVisitors = todayVisitors;
    }

    public long getThisWeekVisitors() {
        return thisWeekVisitors;
    }

    public void setThisWeekVisitors(long thisWeekVisitors) {
        this.thisWeekVisitors = thisWeekVisitors;
    }

    public long getThisMonthVisitors() {
        return thisMonthVisitors;
    }

    public void setThisMonthVisitors(long thisMonthVisitors) {
        this.thisMonthVisitors = thisMonthVisitors;
    }

    public long getProjectViews() {
        return projectViews;
    }

    public void setProjectViews(long projectViews) {
        this.projectViews = projectViews;
    }

    public long getContactFormSubmissions() {
        return contactFormSubmissions;
    }

    public void setContactFormSubmissions(long contactFormSubmissions) {
        this.contactFormSubmissions = contactFormSubmissions;
    }

    public long getResumeDownloads() {
        return resumeDownloads;
    }

    public void setResumeDownloads(long resumeDownloads) {
        this.resumeDownloads = resumeDownloads;
    }

    public Map<String, Long> getVisitorsByCountry() {
        return visitorsByCountry;
    }

    public void setVisitorsByCountry(Map<String, Long> visitorsByCountry) {
        this.visitorsByCountry = visitorsByCountry;
    }

    public Map<String, Long> getVisitorsByDevice() {
        return visitorsByDevice;
    }

    public void setVisitorsByDevice(Map<String, Long> visitorsByDevice) {
        this.visitorsByDevice = visitorsByDevice;
    }

    public Map<String, Long> getVisitorsByBrowser() {
        return visitorsByBrowser;
    }

    public void setVisitorsByBrowser(Map<String, Long> visitorsByBrowser) {
        this.visitorsByBrowser = visitorsByBrowser;
    }

    public Map<String, Long> getMostVisitedPages() {
        return mostVisitedPages;
    }

    public void setMostVisitedPages(Map<String, Long> mostVisitedPages) {
        this.mostVisitedPages = mostVisitedPages;
    }
}