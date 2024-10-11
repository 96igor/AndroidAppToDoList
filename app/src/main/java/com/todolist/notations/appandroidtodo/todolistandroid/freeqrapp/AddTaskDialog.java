package com.todolist.notations.appandroidtodo.todolistandroid.freeqrapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddTaskDialog extends DialogFragment {

    private EditText titleInput;
    private EditText descriptionInput;
    private TaskDialogListener listener;

    public interface TaskDialogListener {
        void onTaskCreated(Task task);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (TaskDialogListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_add_task, null);
        titleInput = dialogView.findViewById(R.id.edit_task_title);
        descriptionInput = dialogView.findViewById(R.id.edit_task_description);
        titleInput.setInputType(InputType.TYPE_CLASS_TEXT);
        descriptionInput.setInputType(InputType.TYPE_CLASS_TEXT);

        builder.setView(dialogView)
                .setTitle("Добавить новую задачу")
                .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String title = titleInput.getText().toString();
                        String description = descriptionInput.getText().toString();
                        Task newTask = new Task(title, description);
                        listener.onTaskCreated(newTask);
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        AddTaskDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
