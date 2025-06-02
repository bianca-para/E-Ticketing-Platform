package org.dev.server.services;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static AuditService instance;
    private static final String AUDIT_FILE = "audit_log.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditService() {
        initializeAuditFile();
    }

    public static AuditService getInstance() {
        if (instance == null) {
            synchronized (AuditService.class) {
                if (instance == null) {
                    instance = new AuditService();
                }
            }
        }
        return instance;
    }

    private void initializeAuditFile() {
        try (FileWriter writer = new FileWriter(AUDIT_FILE, false)) {
            writer.append("Action,Timestamp\n");
            System.out.println("Audit file initialized: " + AUDIT_FILE);
        } catch (IOException e) {
            System.err.println("Failed to initialize audit file: " + e.getMessage());
        }
    }

    public void logAction(String action) {
        String timestamp = LocalDateTime.now().format(formatter);
        try (FileWriter writer = new FileWriter(AUDIT_FILE, true)) {
            writer.append(String.format("%s,%s\n", action, timestamp));
            writer.flush();
            System.out.println("Audit: " + action + " at " + timestamp);
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
    }

    public void logActionWithDetails(String action, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        try (FileWriter writer = new FileWriter(AUDIT_FILE, true)) {
            writer.append(String.format("%s - %s,%s\n", action, details, timestamp));
            writer.flush();
            System.out.println("Audit: " + action + " - " + details + " at " + timestamp);
        } catch (IOException e) {
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
    }
}