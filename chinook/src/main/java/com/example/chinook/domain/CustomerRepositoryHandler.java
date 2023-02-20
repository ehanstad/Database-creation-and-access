package com.example.chinook.domain;

import com.example.chinook.domain.models.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryHandler {
    public static List<Customer> processResultSet(ResultSet result) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while(result.next()) {
            Customer customer = new Customer (
                    result.getInt("customer_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("country"),
                    result.getString("postal_code"),
                    result.getString("phone"),
                    result.getString("email")
            );
            customers.add(customer);
        }
        return customers;
    }
}
