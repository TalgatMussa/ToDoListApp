package com.example.todolistapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.repository.TaskRepository

class TaskViewModelProviderFactory(
    val taskRepository: TaskRepository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(taskRepository) as T
    }
}