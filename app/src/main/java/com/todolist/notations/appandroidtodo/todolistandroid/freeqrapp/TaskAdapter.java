package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.widget.CheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnTaskClickListener onTaskClickListener;

    // Интерфейс для обработки кликов
    public interface OnTaskClickListener {
        void onTaskClick(int position);
        void onDeleteTaskClick(int position); // Новый метод для удаления
    }

    public TaskAdapter(List<Task> taskList, OnTaskClickListener onTaskClickListener) {
        this.taskList = taskList;
        this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.titleTextView.setText(task.getTitle());
        holder.descriptionTextView.setText(task.getDescription());
        holder.checkBox.setChecked(task.isCompleted()); // Устанавливаем состояние CheckBox

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            // Можно сохранять изменения
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTextView;
        public TextView descriptionTextView;
        public CheckBox checkBox;  // Добавляем CheckBox
        OnTaskClickListener onTaskClickListener;

        public TaskViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.task_title);
            descriptionTextView = itemView.findViewById(R.id.task_description);
            checkBox = itemView.findViewById(R.id.task_checkbox);  // Инициализация CheckBox
            this.onTaskClickListener = onTaskClickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(v -> {
                onTaskClickListener.onDeleteTaskClick(getAdapterPosition());
                return true;
            });
        }

        @Override
        public void onClick(View view) {
            onTaskClickListener.onTaskClick(getAdapterPosition());
        }
    }
}

