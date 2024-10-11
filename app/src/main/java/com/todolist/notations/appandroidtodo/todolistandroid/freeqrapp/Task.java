package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

public class Task {
    private String title;
    private String description;
    private boolean isCompleted;
    private long lastViewed;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false;
        this.lastViewed = System.currentTimeMillis(); // текущее время
    }

    // Getters и Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public long getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(long lastViewed) {
        this.lastViewed = lastViewed;
    }
}
