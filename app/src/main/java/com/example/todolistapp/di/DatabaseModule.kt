package com.example.todolistapp.di

import android.content.Context
import androidx.room.Room
import com.example.todolistapp.data.db.TaskDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(
        context: Context
    ): TaskDatabase {
        return Room.databaseBuilder(context, TaskDatabase::class.java, "task_database.db").build()
    }
}