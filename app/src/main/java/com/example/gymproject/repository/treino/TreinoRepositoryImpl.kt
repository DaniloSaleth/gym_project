package com.example.gymproject.repository.treino

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gymproject.Constants.USER_DATA
import com.example.gymproject.Constants.db
import com.example.gymproject.model.FirebaseData
import com.example.gymproject.model.Treino
import com.google.gson.Gson

class TreinoRepositoryImpl : TreinoRepository {
    private val data = MutableLiveData<List<Treino>>()
    private val gson = Gson()

    override suspend fun getTreino(): TreinoRepositoryStatus {
        return try {
            db.collection("user").document(USER_DATA.user_id)
                .addSnapshotListener{ value, _ ->
                    var listaFirebase = gson.toJson(value?.data).toString()
                    var listas = gson.fromJson(listaFirebase, FirebaseData::class.java)

                    data.value = listas.treino
                }

            TreinoRepositoryStatus.GetTreinoSuccess(data.value!!)
        } catch (t : Throwable){
            TreinoRepositoryStatus.Error(t)
        }
    }

    override suspend fun setTreino(treino: Treino): TreinoRepositoryStatus {
        return try {
            getTreino()
            val map = mutableMapOf<String, Any>(
                "treino" to data.value!!.plus(treino)
            )

            var teste = db.collection("user").document(USER_DATA.user_id).update(map)

            TreinoRepositoryStatus.SetTreinoSuccess("O treino foi criado")
        } catch (t : Throwable){
            TreinoRepositoryStatus.Error(t)
        }
    }

    override suspend fun removeTreino(treino: Treino): TreinoRepositoryStatus {
        return try {
            getTreino()
            val map = mutableMapOf<String, Any>(
                "treino" to data.value!!.filter { it != treino }
            )

            db.collection("user").document(USER_DATA.user_id).update(map)

            TreinoRepositoryStatus.RemoveTreinoSuccess("O treino foi removido")
        } catch (t : Throwable){
            TreinoRepositoryStatus.Error(t)
        }
    }
}