package com.example.todolistapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todolistapp.data.model.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}