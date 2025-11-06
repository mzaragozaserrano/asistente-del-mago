package com.asistentedelmago.core.data.di

import android.content.Context
import com.asistentedelmago.core.data.local.AppDatabase
import com.asistentedelmago.core.data.local.TrickDao
import com.asistentedelmago.core.data.repository.TricksRepositoryImpl
import com.asistentedelmago.core.domain.repository.TricksRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<AppDatabase> {
        AppDatabase.create(androidContext())
    }
    
    single<TrickDao> {
        get<AppDatabase>().trickDao()
    }
    
    single<TricksRepository> {
        TricksRepositoryImpl(get())
    }
}

