package com.example.todolistapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.model.Task
import com.example.todolistapp.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class TaskViewModel(
    val taskRepository: TaskRepository
): ViewModel() {

    val searchQuery = MutableStateFlow("")

    private val taskFlow = searchQuery.flatMapLatest {
        taskRepository.getTasks(it)
    }
    val task = taskFlow.asLiveData()

    fun insert(task: Task) = viewModelScope.launch {
        taskRepository.insert(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        taskRepository.update(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        taskRepository.delete(task)
    }

    fun onTaskSelected(task: Task) {

    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        taskRepository.update(task.copy(completed = isChecked))
    }
}
