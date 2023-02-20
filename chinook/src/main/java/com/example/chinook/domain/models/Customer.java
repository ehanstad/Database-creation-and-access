package com.example.chinook.domain.models;

public record Customer(int id, String firstName, String lastName, String country, String postalCode, String phoneNumber, String email) {
}