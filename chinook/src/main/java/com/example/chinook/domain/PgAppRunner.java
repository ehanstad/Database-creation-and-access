package com.example.chinook.domain;

import com.example.chinook.domain.models.Customer;

import com.example.chinook.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PgAppRunner implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public PgAppRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Customer> customers = customerRepository.findAll(10, 10);
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(customers.get(i).firstName());
        }
        Customer cust = customerRepository.findById(1);
        System.out.println(cust.firstName());
//        List<Customer> cust = customerRepository.findByName("");
//        for (int i = 0; i < cust.size(); i++) {
//            System.out.println(cust.get(i).firstName());
//        }

    }
}
