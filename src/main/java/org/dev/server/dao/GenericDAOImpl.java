package org.dev.server.dao;

import org.dev.server.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
    protected Connection connection;

    public GenericDAOImpl() {
        this.connection = DatabaseConfig.getInstance().getConnection();
    }

    protected void handleSQLException(SQLException e) {
        throw new RuntimeException("Database operation failed", e);
    }
}