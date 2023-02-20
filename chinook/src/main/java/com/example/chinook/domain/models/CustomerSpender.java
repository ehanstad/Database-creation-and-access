package com.example.chinook.domain.models;

public record CustomerSpender(int customerId, String firstName, String lastName, int totalTransactions, double totalSpent) {
}
