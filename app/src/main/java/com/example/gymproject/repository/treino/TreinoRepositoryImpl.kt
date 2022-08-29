package com.example.gymproject.repository.treino

import androidx.lifecycle.MutableLiveData
import com.example.gymproject.Constants.USER_DATA
import com.example.gymproject.model.FirebaseData
import com.example.gymproject.model.Treino
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class TreinoRepositoryImpl(private val database: FirebaseFirestore) : TreinoRepository {
    private val data = MutableLiveData<List<Treino>>()
    private val gson = Gson()

    override suspend fun getTreino(): TreinoRepositoryStatus {
        return try {
            database.collection("user").document(USER_DATA.user_id)
                .get().addOnCompleteListener {
                    if(it.isSuccessful) {
                        var listaFirebase = gson.toJson(it.result.data).toString()
                        var listas = gson.fromJson(listaFirebase, FirebaseData::class.java)

                        data.value = listas.treino
                    }
                }.continueWith {
                    TreinoRepositoryStatus.GetTreinoSuccess(data.value!!)
                }.await()
        } catch (t: Throwable) {
                TreinoRepositoryStatus.Error(t)
        }
    }

    override suspend fun getTreinoByName(name: String): TreinoRepositoryStatus {
        return try {
            database.collection("user").document(USER_DATA.user_id)
                .get().addOnCompleteListener{ value ->
                    var listaFirebase = gson.toJson(value.result.data).toString()
                    var listas = gson.fromJson(listaFirebase, FirebaseData::class.java)
                    var response = listas.treino.filter { it.nome.contains(name) }
                    data.value = response
                }.continueWith {
                    TreinoRepositoryStatus.GetTreinoSuccess(data.value!!)
                }.await()
        } catch (t: Throwable) {
            TreinoRepositoryStatus.Error(t)
        }
    }

    override suspend fun setTreino(treino: Treino): TreinoRepositoryStatus {
        return try {
            getTreino()
            treino.id = getId(data.value)
            if (validaTreino(treino) && validaExercicios(treino) && validaCampos(treino)) {
                val map = mutableMapOf<String, Any>(
                    "treino" to data.value!!.plus(treino)
                )

                database.collection("user").document(USER_DATA.user_id).update(map)

                TreinoRepositoryStatus.SetTreinoSuccess("Treino criado")
            } else if (!validaCampos(treino)) {
                TreinoRepositoryStatus.SetTreinoResponse("Preencha todos os campos")
            } else if (!validaExercicios(treino)) {
                TreinoRepositoryStatus.SetTreinoResponse("Adicione exercícios ao seu treino")
            } else {
                TreinoRepositoryStatus.SetTreinoResponse("Já existe um treino com esse nome!")
            }
        } catch (t: Throwable) {
            TreinoRepositoryStatus.Error(t)
        }
    }

    override suspend fun removeTreino(treino: Treino): TreinoRepositoryStatus {
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

    override suspend fun updateTreino(newTreino: Treino, oldTreino: Treino): TreinoRepositoryStatus {
        return try {
            getTreino()
            var validaNome = newTreino.nome == oldTreino.nome || validaTreino(newTreino)

            if (validaExercicios(newTreino) && validaCampos(newTreino) && validaNome) {
                var list = data.value!!.filter { it.nome != oldTreino.nome }
                val map = mutableMapOf<String, Any>(
                    "treino" to list.plus(newTreino)
                )

                database.collection("user").document(USER_DATA.user_id).update(map)

                TreinoRepositoryStatus.SetTreinoSuccess("Treino Editado")
            } else if (!validaCampos(newTreino)) {
                TreinoRepositoryStatus.SetTreinoResponse("Preencha todos os campos")
            } else if (!validaExercicios(newTreino)) {
                TreinoRepositoryStatus.SetTreinoResponse("Adicione exercícios ao seu treino")
            } else {
                TreinoRepositoryStatus.SetTreinoResponse("Já existe um treino com esse nome!")
            }
        } catch (t: Throwable) {
            TreinoRepositoryStatus.Error(t)
        }
    }

    private fun getId (list: List<Treino>?) : Int{
        var newId = 0
        do {
            var notFound = true
            val verifyId = list?.filter { it.id == newId }
            if (verifyId?.isEmpty() == true){
                notFound = false
            } else{
                newId++
            }
        }while (notFound)
        return newId
    }

    private fun validaTreino(treino: Treino): Boolean {
        return data.value!!.filter { it.nome == treino.nome }.isEmpty()
    }

    private fun validaCampos(treino: Treino): Boolean {
        return treino.nome.isNotEmpty()
    }

    private fun validaExercicios(treino: Treino): Boolean {
        return treino.exercicios.isNotEmpty()
    }
}