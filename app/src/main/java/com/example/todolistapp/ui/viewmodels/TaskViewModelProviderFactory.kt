package com.example.todolistapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.data.repository.Repository
import com.example.todolistapp.data.repository.RepositoryImpl
import javax.inject.Inject

class TaskViewModelProviderFactory @Inject constructor(
    private val repository: Repository
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}