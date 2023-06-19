package com.example.todolistapp.di

import com.example.todolistapp.data.repository.Repository
import com.example.todolistapp.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface AppBindModule {
    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository
}