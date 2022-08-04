package com.example.gymproject.di

import com.example.gymproject.repository.exercicio.ExercicioRepository
import com.example.gymproject.repository.exercicio.ExercicioRepositoryImpl
import com.example.gymproject.repository.treino.TreinoRepository
import com.example.gymproject.repository.treino.TreinoRepositoryImpl
import com.example.gymproject.ui.home.HomeViewModel
import com.example.gymproject.ui.treino.TreinoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repository = module {
    single {
        TreinoRepositoryImpl() as TreinoRepository
    }

    single {
        ExercicioRepositoryImpl() as ExercicioRepository
    }
}

val viewModels = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        TreinoViewModel(get(),get())
    }
}