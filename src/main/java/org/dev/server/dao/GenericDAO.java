package org.dev.server.dao;

import java.util.List;

public interface GenericDAO<T> {
    void create(T entity);
    T read(String id);
    void update(T entity);
    void delete(String id);
    List<T> findAll();
}