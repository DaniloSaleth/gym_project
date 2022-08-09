package com.example.gymproject.repository.treino

import com.example.gymproject.model.Treino

sealed class TreinoRepositoryStatus{
    data class GetTreinoSuccess(val response: List<Treino>) : TreinoRepositoryStatus()
    data class SetTreinoSuccess(val response: String) : TreinoRepositoryStatus()
    data class RemoveTreinoSuccess(val response: String) : TreinoRepositoryStatus()
    object Carregar : TreinoRepositoryStatus()
    data class Error(val response : Throwable) : TreinoRepositoryStatus()
}

interface TreinoRepository {
    fun getTreino() : TreinoRepositoryStatus
    fun setTreino(treino: Treino) : TreinoRepositoryStatus
    fun removeTreino(treino: Treino) : TreinoRepositoryStatus
}