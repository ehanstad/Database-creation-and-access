package com.example.chinook.domain.repositories;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.CustomerRepositoryHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {


    private final String url;
    private final String username;
    private final String password;

    public CustomerRepositoryImpl(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // Returns list of Customer objects consisting of all customers in DB
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT customer_id, first_name, last_name, country, postal_code, phone, email FROM customer;";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            customers = CustomerRepositoryHandler.processResultSet(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    @Override
    public List<Customer> findAll(int limit, int offset) {
        String sql = "SELECT customer_id, first_name, last_name, country, postal_code, phone, email FROM customer LIMIT ? OFFSET ?";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            customers = CustomerRepositoryHandler.processResultSet(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customers;
    }

    // Returns Customer object based on id
    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT customer_id, first_name, last_name, country, postal_code, phone, email FROM customer WHERE customer_id = ?";
        Customer customer = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            customer = CustomerRepositoryHandler.processResultSet(statement.executeQuery()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // Returns Customer object based on name
    @Override
    public List<Customer> findByName(String name) {
        name = name.strip();
        String firstName = name;
        String lastName = name;
        if(name.contains(" ")) {
            firstName = name.split(" ")[0];
            lastName = name.split(" ")[1];
        }

        String sql = "SELECT customer_id, first_name, last_name, country, postal_code, phone, email FROM customer WHERE first_name LIKE '%' || ? || '%' OR last_name LIKE '%' || ? || '%'";
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            customers = CustomerRepositoryHandler.processResultSet(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // Adds given Customer object to DB
    @Override
    public int insert(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, country, postal_code, phone, email) VALUES (?, ?, ?, ?, ?, ?)";
        int result = 0;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNumber());
            statement.setString(6, customer.email());

            result = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int update(Customer object) {
        return 0;
    }

    @Override
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }
}
