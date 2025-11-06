package com.asistentedelmago.feature.repertory.di

import com.asistentedelmago.core.domain.repository.TricksRepository
import com.asistentedelmago.core.domain.usecase.CreateTrickUseCase
import com.asistentedelmago.core.domain.usecase.GetAllTricksUseCase
import com.asistentedelmago.feature.repertory.ui.AddTrickViewModel
import com.asistentedelmago.feature.repertory.ui.ArsenalViewModel
import org.koin.dsl.module

val repertoryModule = module {
    factory { GetAllTricksUseCase(get<TricksRepository>()) }
    factory { CreateTrickUseCase(get<TricksRepository>()) }
    
    // Usar factory para ViewModels - koin-androidx-viewmodel no está disponible en 3.5.0
    // Se pueden inyectar como ViewModels usando factory en Compose
    factory { ArsenalViewModel(get()) }
    factory { AddTrickViewModel(get()) }
}

