package com.example.gymproject.di

import com.example.gymproject.repository.exercicio.ExercicioRepository
import com.example.gymproject.repository.exercicio.ExercicioRepositoryImpl
import com.example.gymproject.repository.treino.TreinoRepository
import com.example.gymproject.repository.treino.TreinoRepositoryImpl
import com.example.gymproject.ui.exercicio.ExercicioViewModel
import com.example.gymproject.ui.home.HomeViewModel
import com.example.gymproject.ui.treino.TreinoViewModel
import com.example.gymproject.ui.treinoDetails.TreinoDetailsViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val repository = module {
    single {
        TreinoRepositoryImpl(get()) as TreinoRepository
    }

    single {
        ExercicioRepositoryImpl(get()) as ExercicioRepository
    }
}

val fireBase = module{
    single {
        FirebaseFirestore.getInstance()
    }
}

val viewModels = module {
    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        TreinoDetailsViewModel(get())
    }

    viewModel {
        TreinoViewModel(get(),get())
    }

    viewModel {
        ExercicioViewModel(get())
    }
}