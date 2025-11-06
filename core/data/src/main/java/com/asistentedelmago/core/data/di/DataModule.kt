package com.asistentedelmago.core.data.di

import android.content.Context
import com.asistentedelmago.core.data.local.AppDatabase
import com.asistentedelmago.core.data.local.TrickDao
import com.asistentedelmago.core.data.repository.TricksRepositoryImpl
import com.asistentedelmago.core.domain.repository.TricksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.create(context)
    }
    
    @Provides
    fun provideTrickDao(database: AppDatabase): TrickDao {
        return database.trickDao()
    }
    
    @Provides
    @Singleton
    fun provideTricksRepository(repository: TricksRepositoryImpl): TricksRepository {
        return repository
    }
}

