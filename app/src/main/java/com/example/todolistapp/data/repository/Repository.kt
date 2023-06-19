package com.example.todolistapp.data.repository

import com.example.todolistapp.data.model.Task
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllTasks(): Flow<List<Task>>
    fun searchTasks(searchQuery: String): Flow<List<Task>>
    suspend fun insert(task: Task)
    suspend fun update(task: Task)
    suspend fun delete(task: Task)
}