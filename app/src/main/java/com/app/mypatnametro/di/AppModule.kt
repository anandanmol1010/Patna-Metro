package com.app.mypatnametro.di

import com.app.mypatnametro.data.repository.MetroRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMetroRepository(): MetroRepository {
        return MetroRepository()
    }
}
