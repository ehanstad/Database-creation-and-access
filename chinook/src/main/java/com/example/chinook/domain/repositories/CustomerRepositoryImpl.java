package com.example.chinook.domain.repositories;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.CustomerRepositoryHandler;
import com.example.chinook.domain.models.CustomerCountry;
import com.example.chinook.domain.models.CustomerSpender;
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
            customers = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery());
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
            customers = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery());
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
            customer = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery()).get(0);
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
            customers = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery());
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
    public int update(Customer customer) {
        String sql = "UPDATE customer SET first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, email = ? WHERE customer_id = ?";
        int result = 0;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNumber());
            statement.setString(6, customer.email());
            statement.setInt(7, customer.id());

            result = statement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

    @Override
    public CustomerCountry getMostPopularCountry() {
        String sql = "" +
                "SELECT country, COUNT(country) AS no_customers " +
                "FROM customer " +
                "GROUP BY country " +
                "ORDER BY no_customers DESC " +
                "LIMIT 1";
        CustomerCountry country = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            country = CustomerRepositoryHandler.processCountryResultSet(statement.executeQuery()).get(0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return country;
    }

    // Gets biggest spender
    @Override
    public CustomerSpender getBiggestSpender() {
        String sql = "" +
                "SELECT customer.customer_id, first_name, last_name, COUNT(total) AS total_transactions, SUM(total) AS total " +
                "FROM customer " +
                "INNER JOIN invoice " +
                "ON customer.customer_id = invoice.customer_id " +
                "GROUP BY customer.customer_id " +
                "ORDER BY total DESC " +
                "LIMIT 1";
        CustomerSpender customerSpender = null;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            customerSpender = CustomerRepositoryHandler.processCustomerSpenderResultSet(statement.executeQuery()).get(0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerSpender;
    }

    //SELECT customer.customer_id, first_name, last_name, genre.name, COUNT(genre.name) AS frequence
    //FROM customer, genre, invoice, invoice_line, track
    //WHERE customer.customer_id = invoice.customer_id
    //AND invoice.invoice_id = invoice_line.invoice_id
    //AND invoice_line.track_id = track.track_id
    //AND track.genre_id = genre.genre_id
    //AND customer.customer_id = 4
    //GROUP BY customer.customer_id, genre.name
    //ORDER BY frequence DESC
}
