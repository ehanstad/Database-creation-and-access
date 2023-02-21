package com.example.chinook.domain.models;

import java.util.List;

/**
 *
 * @param customerId
 * @param firstName
 * @param lastName
 * @param genres
 */
public record CustomerGenre(int customerId, String firstName, String lastName, List<String> genres) {
}
