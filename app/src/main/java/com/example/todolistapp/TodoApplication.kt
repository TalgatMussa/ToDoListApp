package com.example.todolistapp

import android.app.Application
import com.example.todolistapp.data.db.TaskDatabase
import com.example.todolistapp.di.ApplicationComponent
import com.example.todolistapp.di.DaggerApplicationComponent

class TodoApplication: Application() {
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}