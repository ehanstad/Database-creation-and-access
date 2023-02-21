package com.example.chinook.domain;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.models.CustomerCountry;
import com.example.chinook.domain.models.CustomerGenre;
import com.example.chinook.domain.models.CustomerSpender;
import org.springframework.beans.MutablePropertyValues;

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

    public static List<CustomerSpender> processCustomerSpenderResultSet(ResultSet result) throws SQLException{
        List<CustomerSpender> customerSpenders = new ArrayList<>();
        while(result.next()) {
            CustomerSpender customerSpender = new CustomerSpender (
                    result.getInt("customer_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getInt("total_transactions"),
                    result.getDouble("total")
            );
            customerSpenders.add(customerSpender);
        }
        return customerSpenders;
    }

    public static List<CustomerGenre> processCustomerGenreResultSet(ResultSet result) throws SQLException {
        List<CustomerGenre> customerGenres = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        int customerId = 0;
        String firstName = "", lastName = "";
        Integer genreFrequency = null;
        while(result.next()) {
            if (genreFrequency == null) {
                genreFrequency = result.getInt("frequency");
                customerId = result.getInt("customer_id");
                firstName = result.getString("first_name");
                lastName = result.getString("last_name");
                genres.add(result.getString("name"));
                continue;
            }
            if (result.getInt("frequency") == genreFrequency) {
                genres.add(result.getString("name"));
                continue;
            }
            break;
        }
        customerGenres.add(new CustomerGenre(customerId, firstName, lastName, genres));
        return customerGenres;
    }
}
