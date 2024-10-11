package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Пример данных для списка задач
        taskList = new ArrayList<>();
        taskList.add(new Task("Buy Groceries", "Milk, Bread, Eggs"));
        taskList.add(new Task("Do Homework", "Math exercises"));
        taskList.add(new Task("Call Mom", "Sunday evening"));

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setAdapter(taskAdapter);
    }
}
