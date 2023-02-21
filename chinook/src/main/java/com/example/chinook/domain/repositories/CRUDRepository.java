package com.example.chinook.domain.repositories;

import java.util.List;

/**
 *
 * @param <T>
 * @param <U>
 */
public interface CRUDRepository<T,U> {
    List<T> findAll();
    T findById(U id);
    int insert(T object);
    int update(T object);
    int delete(T object);
    int deleteById(U id);
}
