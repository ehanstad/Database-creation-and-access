package com.example.chinook.domain.repositories;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.CustomerRepositoryHandler;
import com.example.chinook.domain.models.CustomerCountry;
import com.example.chinook.domain.models.CustomerGenre;
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

    /**
     * Connects to a postgres database and gets all customers with the tables corresponding with the Customer record
     * @return  a list of Customer objects consisting of all customers in DB
     */
    @Override
    public List<Customer> findAll() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "" +
                "SELECT customer_id, first_name, last_name, country, postal_code, phone, email " +
                "FROM customer";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);

            customerList = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return customerList;
    }

    /**
     * Connects to a postgres database and gets all customers with the tables corresponding with the Customer record,
     * limited by a limit and offset
     * @param   limit   the limit for number of customers
     * @param   offset  the offset for when to start
     * @return          a list of Customer objects consisting of customers in DB
     */
    @Override
    public List<Customer> findAll(int limit, int offset) {
        if(limit <= 0 || offset < 0) return null;
        List<Customer> customerList = new ArrayList<>();
        String sql = "" +
                "SELECT customer_id, first_name, last_name, country, postal_code, phone, email " +
                "FROM customer " +
                "LIMIT ? OFFSET ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, limit);
            statement.setInt(2, offset);

            customerList = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return customerList;
    }

    /**
     * Connects to a postgres database and gets a customer with the corresponding id
     * @param   customerId  the id for a customer
     * @return              an instance of Customer based on customerId
     */
    @Override
    public Customer findById(Integer customerId) {
        if(customerId == null) return null;
        Customer customer = null;
        String sql = "" +
                "SELECT customer_id, first_name, last_name, country, postal_code, phone, email " +
                "FROM customer " +
                "WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);

            customer = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery()).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    /**
     * Connects to a postgres database and gets the customers which names corresponds to the param
     * @param   customerName    a string containing first, last, or both first and last name
     * @return                  a list of Customer objects
     */
    @Override
    public List<Customer> findCustomersByName(String customerName) {
        if(customerName == null || customerName.strip().equals("")) return null;
        List<Customer> customerList = new ArrayList<>();
        customerName = customerName.strip();
        String firstName = customerName;
        String lastName = customerName;
        String sql = "" +
                "SELECT customer_id, first_name, last_name, country, postal_code, phone, email " +
                "FROM customer " +
                "WHERE first_name LIKE '%' || ? || '%' " +
                "OR last_name LIKE '%' || ? || '%'";

        if(customerName.contains(" ")) {
            firstName = customerName.split(" ")[0];
            lastName = customerName.split(" ")[1];
        }

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            customerList = CustomerRepositoryHandler.processCustomerResultSet(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }

    /**
     * Adds a customer to the database in the table customer
     * @param   customer  an instance of Customer
     * @return            an int indicating whether insert was successful or not
     */
    @Override
    public int insert(Customer customer) {
        if (customer == null) return 0;
        int result = 0;
        String sql = "" +
                "INSERT INTO customer (first_name, last_name, country, postal_code, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

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

    /**
     * Updates customer with params customer id with the values that the object customer contains
     * @param   customer    an instance of Customer
     * @return              an int indicating whether update was successful or not
     */
    @Override
    public int update(Customer customer) {
        if (customer == null) return 0;
        int result = 0;
        String sql = "" +
                "UPDATE customer SET first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, email = ? " +
                "WHERE customer_id = ?";

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

    /**
     * Deletes the param customer form a postgres database
     * @param   customer    an instance of Customer
     * @return              an int indicating whether delete was successful or not
     */
    @Override
    public int delete(Customer customer) {
        return deleteById(customer.id());
    }

    /**
     * Deletes the customer form a postgres database with the param customer id
     * @param   customerId  an integer containing the customer id
     * @return              an int indicating whether delete was successful or not
     */
    @Override
    public int deleteById(Integer customerId) {
        if (customerId == null) return 0;
        int result = 0;
        String sql = "DELETE FROM customer WHERE customer_id = ?";

        try(Connection conn = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);

            result = statement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gets the country with most customers in a postgres database
     * @return  an instance of CustomerCountry
     */
    @Override
    public CustomerCountry getMostPopularCountry() {
        CustomerCountry country = null;
        String sql = "" +
                "SELECT country, COUNT(country) AS no_customers " +
                "FROM customer " +
                "GROUP BY country " +
                "ORDER BY no_customers DESC " +
                "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            country = CustomerRepositoryHandler.processCountryResultSet(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }

    /**
     * Gets the customer who spent most money from a postgres database
     * @return  an instance of CustomerSpender
     */
    @Override
    public CustomerSpender getBiggestSpender() {
        CustomerSpender customerSpender = null;
        String sql = "" +
                "SELECT customer.customer_id, first_name, last_name, COUNT(total) AS total_transactions, SUM(total) AS total " +
                "FROM customer " +
                "INNER JOIN invoice " +
                "ON customer.customer_id = invoice.customer_id " +
                "GROUP BY customer.customer_id " +
                "ORDER BY total DESC " +
                "LIMIT 1";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            customerSpender = CustomerRepositoryHandler.processCustomerSpenderResultSet(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerSpender;
    }

    /**
     * Gets the most popular genres for a specific customer from a postgres database
     * @param   customerId  the id for a customer
     * @return              an instance of CustomerGenre
     */
    @Override
    public CustomerGenre getMostPopularGenres(Integer customerId) {
        if (customerId == null) return null;
        CustomerGenre customerGenre = null;
        String sql = "" +
                "SELECT COUNT(genre.name) AS frequency, customer.customer_id, first_name, last_name, genre.name " +
                "FROM customer, genre, invoice, invoice_line, track " +
                "WHERE customer.customer_id = invoice.customer_id " +
                "AND invoice.invoice_id = invoice_line.invoice_id " +
                "AND invoice_line.track_id = track.track_id " +
                "AND track.genre_id = genre.genre_id " +
                "AND customer.customer_id = ? " +
                "GROUP BY customer.customer_id, genre.name " +
                "ORDER BY frequency DESC";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, customerId);

            customerGenre = CustomerRepositoryHandler.processCustomerGenreResultSet(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerGenre;
    }
}
