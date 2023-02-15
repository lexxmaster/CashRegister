package com.cashregister.model.dao;

import com.cashregister.model.entity.Entity;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<K, T extends Entity> {
    protected Connection connection;

    public abstract List<T> findAll();

    public abstract Optional<T> findById(K id);
    public abstract int getRecordsCount();

    public abstract boolean create(T entity);

    public abstract boolean update(T entity);

    public abstract boolean delete(T entity);

    public int getNumOfPages(int recordsPerPage) {
        int recordsCount = this.getRecordsCount();
        return (int) Math.ceil(recordsCount * 1.0/ recordsPerPage);
    }
}
