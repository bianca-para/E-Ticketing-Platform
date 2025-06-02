package org.dev.server.models;

import java.util.*;
public class Admin extends User {
    private String adminLevel = "SUPER_ADMIN";
    private List<String> moderatorIds = new ArrayList<>();
    private Map<String, String> systemSettings = new HashMap<>();
    private List<String>  auditLogEntries = new ArrayList<>();
    private List<String>  financialReportIds = new ArrayList<>();

    public Admin(String id, String name, String email) {
        super(id, name, email);
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public List<String> getModeratorIds() {
        return moderatorIds;
    }

    public Map<String, String> getSystemSettings() {
        return systemSettings;
    }

    public List<String> getAuditLogEntries() {
        return auditLogEntries;
    }

    public List<String> getFinancialReportIds() {
        return financialReportIds;
    }

    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }

    public void setModeratorIds(List<String> moderatorIds) {
        this.moderatorIds = moderatorIds;
    }

    public void setSystemSettings(Map<String, String> systemSettings) {
        this.systemSettings = systemSettings;
    }

    public void setAuditLogEntries(List<String> auditLogEntries) {
        this.auditLogEntries = auditLogEntries;
    }

    public void setFinancialReportIds(List<String> financialReportIds) {
        this.financialReportIds = financialReportIds;
    }

    public void addModerator(String moderatorId) {
        moderatorIds.add(moderatorId);
        auditLogEntries.add("main.java.org.dev.server.model.Moderator adaugat: " + moderatorId + " la " + new Date());
    }

    public void updateSystemSetting(String key, String value) {
        systemSettings.put(key, value);
        auditLogEntries.add("Setari updatate: " + key + " = " + value + " la " + new Date());
    }

    public void addFinancialReport(String reportId) {
        financialReportIds.add(reportId);
        auditLogEntries.add("Raport financiar creat: " + reportId + " la " + new Date());
    }

    @Override
    public String toString() {
        return "main.java.org.dev.server.model.Admin{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", adminLevel='" + adminLevel + '\'' +
                ", moderatorIds=" + moderatorIds +
                ", systemSettings=" + systemSettings +
                ", auditLogEntries=" + auditLogEntries +
                ", financialReportIds=" + financialReportIds +
                '}';
    }
}
