package com.asistentedelmago.feature.repertory.di

import com.asistentedelmago.core.domain.repository.TricksRepository
import com.asistentedelmago.core.domain.usecase.CreateTrickUseCase
import com.asistentedelmago.core.domain.usecase.GetAllTricksUseCase
import com.asistentedelmago.feature.repertory.ui.AddTrickViewModel
import com.asistentedelmago.feature.repertory.ui.ArsenalViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repertoryModule = module {
    factory { GetAllTricksUseCase(get<TricksRepository>()) }
    factory { CreateTrickUseCase(get<TricksRepository>()) }
    
    viewModel { ArsenalViewModel(get()) }
    viewModel { AddTrickViewModel(get()) }
}

