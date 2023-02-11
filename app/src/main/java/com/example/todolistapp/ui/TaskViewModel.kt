package com.example.todolistapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.model.Task
import com.example.todolistapp.repository.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(
    val taskRepository: TaskRepository
): ViewModel() {

    fun insert(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        taskRepository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
    }
}
