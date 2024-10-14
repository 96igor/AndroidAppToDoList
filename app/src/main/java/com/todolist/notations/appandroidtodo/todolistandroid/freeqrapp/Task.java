package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

public class Task {
    private String title;
    private String description;
    private boolean isCompleted; // Поле для хранения состояния задачи
    private long lastViewed; // Поле для хранения времени последнего просмотра задачи

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.isCompleted = false; // По умолчанию задача не завершена
        this.lastViewed = System.currentTimeMillis(); // Устанавливаем текущее время при создании задачи
    }

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