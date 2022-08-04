package com.example.gymproject.repository.treino

import com.example.gymproject.model.Treino

sealed class TreinoRepositoryStatus{
    data class GetTreinoSuccess(val response: List<Treino>) : TreinoRepositoryStatus()
    data class SetTreinoSuccess(val response: String) : TreinoRepositoryStatus()
    data class RemoveTreinoSuccess(val response: String) : TreinoRepositoryStatus()
    data class Error(val response : Throwable) : TreinoRepositoryStatus()
}

interface TreinoRepository {
    suspend fun getTreino() : TreinoRepositoryStatus
    suspend fun setTreino(treino: Treino) : TreinoRepositoryStatus
    suspend fun removeTreino(treino: Treino) : TreinoRepositoryStatus
}