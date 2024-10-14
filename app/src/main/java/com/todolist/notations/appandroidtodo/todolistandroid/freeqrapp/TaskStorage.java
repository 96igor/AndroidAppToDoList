package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskStorage {
    private Context context;

    public TaskStorage(Context context) {
        this.context = context;
    }

    public void saveTasks(List<Task> taskList) {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        SharedPreferences sharedPreferences = context.getSharedPreferences("task_storage", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasks", json);
        editor.apply();
    }

    public List<Task> loadTasks() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("task_storage", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("tasks", null);
        Type type = new TypeToken<List<Task>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }
}
