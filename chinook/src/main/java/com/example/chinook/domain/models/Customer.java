package com.example.chinook.domain.models;

/**
 *
 * @param id
 * @param firstName
 * @param lastName
 * @param country
 * @param postalCode
 * @param phoneNumber
 * @param email
 */
public record Customer(Integer id, String firstName, String lastName, String country, String postalCode, String phoneNumber, String email) {
}