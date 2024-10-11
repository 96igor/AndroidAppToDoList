package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private static final String PREFS_NAME = "task_storage";
    private static final String KEY_TASKS = "tasks";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public TaskStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    // Сохранение задач
    public void saveTasks(List<Task> tasks) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(tasks);
        editor.putString(KEY_TASKS, json);
        editor.apply();
    }

    // Загрузка задач
    public List<Task> loadTasks() {
        String json = sharedPreferences.getString(KEY_TASKS, null);
        if (json == null) {
            // Если данных нет, вернуть пустой список
            return new ArrayList<>();
        }
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
