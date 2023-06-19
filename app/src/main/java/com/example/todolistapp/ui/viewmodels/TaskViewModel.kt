package com.example.todolistapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.model.Task
import com.example.todolistapp.data.repository.Repository
import com.example.todolistapp.data.repository.RepositoryImpl
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: Repository): ViewModel() {

    val task = repository.getAllTasks().asLiveData()
    fun searchTasks(searchQuery: String): LiveData<List<Task>> {
        return repository.searchTasks(searchQuery).asLiveData()
    }

    fun insert(task: Task) = viewModelScope.launch {
        repository.insert(task)
    }

    fun delete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun update(task: Task) = viewModelScope.launch {
        repository.update(task)
    }
}
