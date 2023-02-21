package com.example.chinook.domain.models;

import java.util.List;

/**
 * A model of a customers information and it´s most popular genres
 * @param customerId
 * @param firstName
 * @param lastName
 * @param genres
 */
public record CustomerGenre(int customerId, String firstName, String lastName, List<String> genres) { }
