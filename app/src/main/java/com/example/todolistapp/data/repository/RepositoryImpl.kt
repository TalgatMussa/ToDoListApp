package com.example.todolistapp.data.repository

import com.example.todolistapp.data.db.TaskDatabase
import com.example.todolistapp.data.model.Task
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val database: TaskDatabase): Repository {
    override suspend fun insert(task: Task) = database.taskDao().insert(task)
    override suspend fun update(task: Task) = database.taskDao().update(task)
    override suspend fun delete(task: Task) = database.taskDao().delete(task)
    override fun getAllTasks() = database.taskDao().getAllTasks()
    override fun searchTasks(searchQuery: String) = database.taskDao().searchTasks(searchQuery)
}