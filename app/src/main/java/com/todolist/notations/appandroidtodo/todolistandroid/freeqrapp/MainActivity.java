package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList;
    private FloatingActionButton fabAddTask;
    private static final int REQUEST_CODE_ADD_TASK = 1; // Код для возврата результата

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация элементов
        recyclerView = findViewById(R.id.recycler_view);
        fabAddTask = findViewById(R.id.fab_add_task);

        // Загрузка задач
        loadTasks();

        // Настройка адаптера и RecyclerView
        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(int position) {
                showEditTaskDialog(position); // Открыть диалог для редактирования задачи
            }

            @Override
            public void onDeleteTaskClick(int position) {
                showDeleteTaskDialog(position); // Открыть диалог для удаления задачи
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Открытие формы для добавления новой задачи при нажатии на FloatingActionButton
        fabAddTask.setOnClickListener(view -> openAddTaskForm());
    }

    // Метод для открытия формы добавления новой задачи
    private void openAddTaskForm() {
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_TASK); // Старт активности для добавления задачи
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_TASK && resultCode == RESULT_OK) {
            if (data != null) {
                String title = data.getStringExtra("title");
                String description = data.getStringExtra("description");

                addNewTask(title, description); // Добавление новой задачи
            }
        }
    }

    // Метод для добавления новой задачи
    private void addNewTask(String title, String description) {
        Task newTask = new Task(title, description);
        taskList.add(newTask);
        taskAdapter.notifyItemInserted(taskList.size() - 1);
        saveTasks(); // Сохраняем задачи
    }

    // Метод для отображения диалогового окна редактирования задачи
    private void showEditTaskDialog(int position) {
        Task task = taskList.get(position);
        task.setLastViewed(System.currentTimeMillis());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Редактировать задачу");

        View viewInflated = getLayoutInflater().inflate(R.layout.dialog_edit_task, null);
        final EditText inputTitle = viewInflated.findViewById(R.id.edit_task_title);
        final EditText inputDescription = viewInflated.findViewById(R.id.edit_task_description);

        inputTitle.setText(task.getTitle());
        inputDescription.setText(task.getDescription());

        builder.setView(viewInflated);

        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            task.setTitle(inputTitle.getText().toString());
            task.setDescription(inputDescription.getText().toString());
            taskAdapter.notifyItemChanged(position);
            Toast.makeText(MainActivity.this, "Задача обновлена", Toast.LENGTH_SHORT).show();
            saveTasks(); // Сохраняем задачи после редактирования
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Метод для отображения диалогового окна удаления задачи
    private void showDeleteTaskDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Удалить задачу");
        builder.setMessage("Вы уверены, что хотите удалить эту задачу?");

        builder.setPositiveButton("Удалить", (dialog, which) -> {
            taskList.remove(position);
            taskAdapter.notifyItemRemoved(position);
            Toast.makeText(MainActivity.this, "Задача удалена", Toast.LENGTH_SHORT).show();
            saveTasks(); // Сохраняем задачи после удаления
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    // Метод для сохранения задач
    private void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("task_list", json);
        editor.apply();
    }

    // Метод для загрузки задач
    private void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("tasks", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task_list", null);
        Type type = new TypeToken<List<Task>>() {}.getType();
        taskList = gson.fromJson(json, type);
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
    }
}