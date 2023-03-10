package com.example.chinook.domain.models;

/**
 * A model of a customer's and their transactions
 * @param customerId
 * @param firstName
 * @param lastName
 * @param totalTransactions
 * @param totalSpent
 */
public record CustomerSpender(int customerId, String firstName, String lastName, int totalTransactions, double totalSpent) { }
