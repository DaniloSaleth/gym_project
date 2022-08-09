package com.example.gymproject.repository.treino

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.gymproject.Constants.USER_DATA
import com.example.gymproject.model.FirebaseData
import com.example.gymproject.model.Treino
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson

class TreinoRepositoryImpl(private val database: FirebaseFirestore) : TreinoRepository {
    private val data = MutableLiveData<List<Treino>>()
    private val gson = Gson()

    override fun getTreino(): TreinoRepositoryStatus {
        return try {
            database.collection("user").document(USER_DATA.user_id)
                .addSnapshotListener { value, errorFireBase ->
                    var listaFirebase = gson.toJson(value?.data).toString()
                    var listas = gson.fromJson(listaFirebase, FirebaseData::class.java)

                    data.value = listas.treino
                }
            TreinoRepositoryStatus.GetTreinoSuccess(data.value!!)
        } catch (t: Throwable) {
            if (t != null) {
                TreinoRepositoryStatus.Error(t)
            } else {
                TreinoRepositoryStatus.Carregar
            }
        }
    }

    override fun setTreino(treino: Treino): TreinoRepositoryStatus {
        return try {
            getTreino()

            var validaTreino = data.value!!.filter { it.nome == treino.nome }

            if (validaTreino.isEmpty()) {
                val map = mutableMapOf<String, Any>(
                    "treino" to data.value!!.plus(treino)
                )

                database.collection("user").document(USER_DATA.user_id).update(map)

                TreinoRepositoryStatus.SetTreinoSuccess("Treino criado")
            } else {
                TreinoRepositoryStatus.SetTreinoResponse("Já existe um treino com esse nome!")
            }
        } catch (t: Throwable) {
            TreinoRepositoryStatus.Error(t)
        }
    }

    override fun removeTreino(treino: Treino): TreinoRepositoryStatus {
        return try {
            getTreino()
            val map = mutableMapOf<String, Any>(
                "treino" to data.value!!.filter { it.nome != treino.nome }
            )

            database.collection("user").document(USER_DATA.user_id).update(map)

            TreinoRepositoryStatus.RemoveTreinoSuccess("O treino foi removido")
        } catch (t: Throwable) {
            TreinoRepositoryStatus.Error(t)
        }
    }
}