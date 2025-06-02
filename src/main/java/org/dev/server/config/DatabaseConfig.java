package org.dev.server.config;

import java.sql.*;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class DatabaseConfig {
    private static final String URL = "jdbc:postgresql://localhost:5432/eventplanner";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1981";
    private static final String DRIVER = "org.postgresql.Driver";

    private static DatabaseConfig instance;
    private Connection connection;

    private DatabaseConfig() {
        try {
            Class.forName(DRIVER);
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("Database connected successfully!");
            System.out.println("URL: " + URL);
            System.out.println("User: " + USERNAME);

            initializeDatabase();

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            throw new RuntimeException("Database connection failed", e);
        }
    }

    public static DatabaseConfig getInstance() {
        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get connection", e);
        }
        return connection;
    }

    private void initializeDatabase() {

        List<String> migrationFiles = Arrays.asList(
                "V1_DDL_Create_Venue.sql",
                "V2_DDL_Create_User.sql",
                "V3_DDL_Create_Event.sql",
                "V4_DDL_Create_Ticket.sql",
                "V5_DDL_Create_Event_Attendees.sql"
        );

        for (String fileName : migrationFiles) {
            executeMigrationFile(fileName);
        }
    }

    private void executeMigrationFile(String fileName) {
        try {
            //cauta fisierele
            String resourcePath = "migrations/" + fileName;
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);

            if (inputStream == null) {
                System.out.println("Migration file not found: " + fileName);
                return;
            }

            StringBuilder sqlContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.startsWith("--") && !line.isEmpty()) {
                        sqlContent.append(line).append("\n");
                    }
                }
            }

            if (sqlContent.length() > 0) {
                try (Statement statement = connection.createStatement()) {
                    statement.execute(sqlContent.toString());
                    System.out.println("Executed: " + fileName);
                }
            }

        } catch (SQLException | IOException e) {
            System.err.println("Failed to execute migration " + fileName + ": " + e.getMessage());
        }
    }

}