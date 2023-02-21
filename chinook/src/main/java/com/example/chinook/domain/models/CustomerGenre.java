package com.example.chinook.domain.models;

import java.util.List;

public record CustomerGenre(int customerId, String firstName, String lastName, List<String> genres) {
}
