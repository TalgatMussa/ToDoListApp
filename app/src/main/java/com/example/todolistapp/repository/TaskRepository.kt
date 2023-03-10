package com.example.todolistapp.repository

import com.example.todolistapp.db.TaskDatabase
import com.example.todolistapp.model.Task

class TaskRepository(private val db: TaskDatabase) {
    fun getAllTasks() = db.taskDao().getAllTasks()

    fun searchTasks(searchQuery: String) = db.taskDao().searchTasks(searchQuery)

    suspend fun insert(task: Task) = db.taskDao().insert(task)

    suspend fun update(task: Task) = db.taskDao().update(task)

    suspend fun delete(task: Task) = db.taskDao().delete(task)
}