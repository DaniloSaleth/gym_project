package com.example.gymproject.repository.exercicio

import androidx.lifecycle.MutableLiveData
import com.example.gymproject.Constants
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.FirebaseData
import com.google.gson.Gson

class ExercicioRepositoryImpl : ExercicioRepository {
    private val data = MutableLiveData<List<Exercicio>>()
    private val gson = Gson()

    override fun getExercicio(): ExercicioRepositoryStatus {
        return try {
            Constants.db.collection("user").document(Constants.USER_DATA.user_id)
                .addSnapshotListener{ value, _ ->
                    var listaFirebase = gson.toJson(value?.data).toString()
                    var listas = gson.fromJson(listaFirebase, FirebaseData::class.java)

                    data.value = listas.exercicio
                }

            ExercicioRepositoryStatus.GetExercicioSuccess(data.value!!)
        } catch (t : Throwable){
            ExercicioRepositoryStatus.Error(t)
        }
    }

    override fun setExercicio(exercicio: Exercicio): ExercicioRepositoryStatus {
        return try {
            getExercicio()
            val map = mutableMapOf<String, Any>(
                "exercicio" to data.value!!.plus(exercicio)
            )

            Constants.db.collection("user").document(Constants.USER_DATA.user_id).update(map)

            ExercicioRepositoryStatus.SetExercicioSuccess("O Exercicio foi criado")
        } catch (t : Throwable){
            ExercicioRepositoryStatus.Error(t)
        }
    }

    override fun removeExercicio(exercicio: Exercicio): ExercicioRepositoryStatus {
        return try {
            getExercicio()
            val map = mutableMapOf<String, Any>(
                "exercicio" to data.value!!.filter { it != exercicio }
            )

            Constants.db.collection("user").document(Constants.USER_DATA.user_id).update(map)

            ExercicioRepositoryStatus.SetExercicioSuccess("O Exercicio foi criado")
        } catch (t : Throwable){
            ExercicioRepositoryStatus.Error(t)
        }
    }
}