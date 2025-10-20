package com.venler42.tamu_dues_api.model;

import java.time.LocalDateTime;

public class Assignment {
    private String name;
    private String description;
    private LocalDateTime dueDate;

    // Getters and setters needed to update requests
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}