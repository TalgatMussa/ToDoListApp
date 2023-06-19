package com.example.todolistapp.di

import android.content.Context
import com.example.todolistapp.ui.fragments.AddEditTaskFragment
import com.example.todolistapp.ui.fragments.TasksFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class, AppBindModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

    fun inject(addEditTaskFragment: AddEditTaskFragment)
    fun inject(taskFragment: TasksFragment)
}