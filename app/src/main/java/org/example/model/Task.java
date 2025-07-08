package org.example.model;

import java.time.LocalDate;

public class Task {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String color;
    private String description;

    public Task(String title, LocalDate startDate, LocalDate endDate, String color, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.description = description;
    }

    // TODO: etc
}
