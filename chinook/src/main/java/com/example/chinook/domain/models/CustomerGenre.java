package com.example.chinook.domain.models;

import java.util.List;

/**
 * A model of a customer's information and their most popular genres
 * @param customerId
 * @param firstName
 * @param lastName
 * @param genres
 */
public record CustomerGenre(int customerId, String firstName, String lastName, List<String> genres) { }
