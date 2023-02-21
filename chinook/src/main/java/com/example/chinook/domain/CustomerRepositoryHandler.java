package com.example.chinook.domain;

import com.example.chinook.domain.models.Customer;
import com.example.chinook.domain.models.CustomerCountry;
import com.example.chinook.domain.models.CustomerGenre;
import com.example.chinook.domain.models.CustomerSpender;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepositoryHandler {

    /**
     * Processes a result set and creates customer object from the data.
     * @param result a result set of data
     * @return a list of customers
     * @throws SQLException an instance of a SQLException
     */
    public static List<Customer> processCustomerResultSet(ResultSet result) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        while(result.next()) {
            customers.add(new Customer (
                    result.getInt("customer_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getString("country"),
                    result.getString("postal_code"),
                    result.getString("phone"),
                    result.getString("email")
            ));
        }
        return customers;
    }

    /**
     * Processes a result set and creates customer country object from the data.
     * @param result a result set of data
     * @return an object of customer countries
     * @throws SQLException an instance of a SQLException
     */
    public static CustomerCountry processCountryResultSet(ResultSet result) throws SQLException {
        CustomerCountry country = null;
        while(result.next()) {
            country = new CustomerCountry (
                    result.getString("country"),
                    result.getInt("no_customers")
            );
        }
        return country;
    }

    /**
     * Processes a result set and creates customer spender object from the data.
     * @param result a result set of data
     * @return an object of customer spenders
     * @throws SQLException an instance of a SQLException
     */
    public static CustomerSpender processCustomerSpenderResultSet(ResultSet result) throws SQLException{
        CustomerSpender customerSpender = null;
        while(result.next()) {
            customerSpender = new CustomerSpender (
                    result.getInt("customer_id"),
                    result.getString("first_name"),
                    result.getString("last_name"),
                    result.getInt("total_transactions"),
                    result.getDouble("total")
            );
        }
        return customerSpender;
    }

    /**
     * Processes a result set and creates customer genre object from the data.
     * @param result a result set of data
     * @return an object of customer genre
     * @throws SQLException an instance of a SQLException
     */
    public static CustomerGenre processCustomerGenreResultSet(ResultSet result) throws SQLException {
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

        return new CustomerGenre(customerId, firstName, lastName, genres);
    }
}
