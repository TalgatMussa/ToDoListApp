package com.example.todolistapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.model.Task
import com.example.todolistapp.repository.TaskRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(private val taskRepository: TaskRepository): ViewModel() {

    val task = taskRepository.getAllTasks().asLiveData()
    fun searchTasks(searchQuery: String): LiveData<List<Task>> {
        return taskRepository.searchTasks(searchQuery).asLiveData()
    }

    fun insert(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        taskRepository.update(task)
    }
}
