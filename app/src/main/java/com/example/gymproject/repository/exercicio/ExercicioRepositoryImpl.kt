package com.example.gymproject.repository.exercicio

import androidx.lifecycle.MutableLiveData
import com.example.gymproject.Constants
import com.example.gymproject.Constants.USER_DATA
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.FirebaseData
import com.example.gymproject.model.Treino
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

class ExercicioRepositoryImpl(private val database: FirebaseFirestore) : ExercicioRepository {
    private val data = MutableLiveData<List<Exercicio>>()
    private val dataTreinos = MutableLiveData<List<Treino>>()
    private val error = MutableLiveData<Throwable>()
    private val gson = Gson()

    override suspend fun getExercicio(): ExercicioRepositoryStatus {
        return try {
            database.collection("user").document(Constants.USER_DATA.user_id)
                .get().addOnCompleteListener { value ->
                    var listaFirebase = gson.toJson(value.result.data).toString()
                    var listas = gson.fromJson(listaFirebase, FirebaseData::class.java)
                    dataTreinos.value = listas.treino
                    data.value = listas.exercicio
                }.continueWith {
                    ExercicioRepositoryStatus.GetExercicioSuccess(data.value!!)
                }.await()
        } catch (t: Throwable) {
            if (t != null) {
                ExercicioRepositoryStatus.Error(t)
            } else {
                ExercicioRepositoryStatus.Carregar
            }
        }
    }

    override suspend fun setExercicio(exercicio: Exercicio): ExercicioRepositoryStatus {
        return try {
            getExercicio()
            exercicio.id = getId(data.value)
            if (validaNome(exercicio)) {
                val map = mutableMapOf<String, Any>(
                    "exercicio" to data.value!!.plus(exercicio)
                )

                database.collection("user").document(USER_DATA.user_id).update(map)

                ExercicioRepositoryStatus.SetExercicioSuccess("O Exercicio foi criado")
            } else {
                ExercicioRepositoryStatus.SetExercicioResponse("Já existe um exercício com esse nome")
            }
        } catch (t: Throwable) {
            ExercicioRepositoryStatus.Error(t)
        }
    }

    override suspend fun removeExercicio(exercicio: Exercicio): ExercicioRepositoryStatus {
        return try {
            getExercicio()
                val map = mutableMapOf<String, Any>(
                    "exercicio" to data.value!!.filter { it != exercicio }
                )

                database.collection("user").document(Constants.USER_DATA.user_id).update(map)

                ExercicioRepositoryStatus.RemoveExercicioSuccess("O Exercicio foi Removido")
        } catch (t: Throwable) {
            ExercicioRepositoryStatus.Error(t)
        }
    }

    private fun validaNome(exercicio: Exercicio): Boolean {
        return data.value!!.filter { it.nome == exercicio.nome }.isEmpty()
    }

    private fun getId(list: List<Exercicio>?): Int {
        var newId = 0
        do {
            var notFound = true
            val verifyId = list?.filter { it.id == newId }
            if (verifyId?.isEmpty() == true) {
                notFound = false
            } else {
                newId++
            }
        } while (notFound)
        return newId
    }
}