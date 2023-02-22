package com.example.chinook.domain;

import com.example.chinook.domain.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PgAppRunner implements ApplicationRunner {

    private final CustomerRepository customerRepository;

    @Autowired
    public PgAppRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     *
     * @param   args    the run args
     */
    @Override
    public void run(ApplicationArguments args) {
    }
}
