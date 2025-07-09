package org.example.model;

import java.time.LocalDateTime;

public class Task {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String color;
    private String description;

    public Task(String title, LocalDateTime startDate, LocalDateTime endDate, String color, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getColor() {
        return color;
    }

    public String getDescription() {
        return description;
    }
    // TODO: etc
}
