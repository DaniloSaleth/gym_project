package com.example.gymproject.repository.exercicio

import com.example.gymproject.model.Exercicio
import com.example.gymproject.repository.treino.TreinoRepositoryStatus

sealed class ExercicioRepositoryStatus{
    data class GetExercicioSuccess(val response: List<Exercicio>) : ExercicioRepositoryStatus()
    data class SetExercicioSuccess(val response: String) : ExercicioRepositoryStatus()
    data class RemoveExercicioSuccess(val response: String) : ExercicioRepositoryStatus()
    object Carregar : ExercicioRepositoryStatus()
    data class Error(val response : Throwable) : ExercicioRepositoryStatus()
}

interface ExercicioRepository {
    fun getExercicio() : ExercicioRepositoryStatus
    fun setExercicio(exercicio: Exercicio) : ExercicioRepositoryStatus
    fun removeExercicio(exercicio: Exercicio) : ExercicioRepositoryStatus
}