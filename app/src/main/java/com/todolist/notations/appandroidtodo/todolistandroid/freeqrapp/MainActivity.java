package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(int position) {
                showEditTaskDialog(position); // Открыть диалог для редактирования задачи
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        // Добавление новой задачи при нажатии на FloatingActionButton
        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask("Новая задача", "Описание новой задачи");
                Toast.makeText(MainActivity.this, "Задача добавлена", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Метод для добавления новой задачи
    private void addNewTask(String title, String description) {
        Task newTask = new Task(title, description);
        taskList.add(newTask);
        taskAdapter.notifyItemInserted(taskList.size() - 1);
    }

    // Метод для отображения диалогового окна редактирования задачи
    private void showEditTaskDialog(int position) {
        Task task = taskList.get(position);

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
        });

        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}

