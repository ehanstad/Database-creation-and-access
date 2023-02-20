package com.example.chinook.domain.repositories;

import com.example.chinook.domain.models.Customer;

import java.util.List;

public interface CustomerRepository extends CRUDRepository<Customer, Integer> {
    List<Customer> findByName(String name);
    List<Customer> findAll(int limit, int offset);
}
