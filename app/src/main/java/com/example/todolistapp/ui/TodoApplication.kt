package com.example.todolistapp.ui

import android.app.Application
import com.example.todolistapp.db.TaskDatabase

class TodoApplication: Application() {
    val database: TaskDatabase by lazy { TaskDatabase.getDatabase(this)}
}