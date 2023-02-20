package com.example.chinook.domain;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.models.CustomerCountry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryHandler {
    public static List<Customer> processCustomerResultSet(ResultSet result) throws SQLException {
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

    public static List<CustomerCountry> processCountryResultSet(ResultSet result) throws SQLException {
        List<CustomerCountry> countries = new ArrayList<>();
        while(result.next()) {
            CustomerCountry country = new CustomerCountry (
                    result.getString("country"),
                    result.getInt("no_customers")
            );
            countries.add(country);
        }
        return countries;
    }
}
