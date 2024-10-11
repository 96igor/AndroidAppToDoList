package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddTaskDialog.TaskDialogListener {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        recyclerView = findViewById(R.id.recycler_view);
        fabAddTask = findViewById(R.id.fab_add_task);

        // Инициализация списка задач
        taskList = new ArrayList<>();
        taskList.add(new Task("Задача 1", "Описание 1"));
        taskList.add(new Task("Задача 2", "Описание 2"));

        // Настройка адаптера и RecyclerView
        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Добавление новой задачи при нажатии на FloatingActionButton
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Открытие диалога добавления задачи
                AddTaskDialog dialog = new AddTaskDialog();
                dialog.show(getSupportFragmentManager(), "AddTaskDialog");
            }
        });
    }

    @Override
    public void onTaskCreated(Task task) {
        taskList.add(task);
        taskAdapter.notifyItemInserted(taskList.size() - 1);
        Toast.makeText(this, "Задача добавлена", Toast.LENGTH_SHORT).show();
    }
}

