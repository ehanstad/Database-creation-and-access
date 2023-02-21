package com.example.chinook.domain.models;

/**
 * A model for a country with registered customers
 * @param countryName The name of the country
 * @param registeredCustomers the number of registered customers
 */
public record CustomerCountry(String countryName, int registeredCustomers) { }
