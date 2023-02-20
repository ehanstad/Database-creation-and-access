package com.example.chinook.domain.repositories;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.models.CustomerCountry;

import java.util.List;

public interface CustomerRepository extends CRUDRepository<Customer, Integer> {
    List<Customer> findAll(int limit, int offset);
    List<Customer> findByName(String name);
    CustomerCountry mostPopularCountry();
}
