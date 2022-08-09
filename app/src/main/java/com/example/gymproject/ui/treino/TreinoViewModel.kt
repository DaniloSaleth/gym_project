package com.example.gymproject.ui.treino

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.Treino
import com.example.gymproject.repository.exercicio.ExercicioRepository
import com.example.gymproject.repository.exercicio.ExercicioRepositoryStatus
import com.example.gymproject.repository.treino.TreinoRepository
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import kotlinx.coroutines.launch

class TreinoViewModel(
    private val exercicioRepository: ExercicioRepository,
    private val treinoRepository: TreinoRepository,
) : ViewModel() {

    private val _currentMsg = MutableLiveData<String>()
    val currentMsg: LiveData<String>
        get() = _currentMsg

    private val _carregar = MutableLiveData<Boolean>()
    val carregar: LiveData<Boolean>
        get() = _carregar

    private val _exercicio = MutableLiveData<List<Exercicio>>()
    val exercicio: LiveData<List<Exercicio>>
        get() = _exercicio

    private val _finish = MutableLiveData<Boolean>()
    val finish : LiveData<Boolean>
        get() = _finish

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _exerciciosToAdd = MutableLiveData<List<Exercicio>>(listOf())
    var exerciciosToAdd = MutableLiveData<List<Exercicio>>()
        get() = _exerciciosToAdd

    fun addToTreino(exercicio: Exercicio) {
        val valida = _exerciciosToAdd.value!!.filter { it.nome == exercicio.nome }
        if (valida.isEmpty()) {
            var list = _exerciciosToAdd.value!!.plus(exercicio)
            _exerciciosToAdd.value = list
        }
    }

    fun removeToTreino(exercicio: Exercicio) {
        val valida = _exerciciosToAdd.value!!.filter { it.nome == exercicio.nome }

        if (valida.isNotEmpty()) {
            var list = _exerciciosToAdd.value!!.minus(exercicio)
            _exerciciosToAdd.value = list

        }
    }

    fun getExercicio() = viewModelScope.launch {
        exercicioRepository.getExercicio().apply {
            when (this) {
                is ExercicioRepositoryStatus.GetExercicioSuccess -> {
                    _exercicio.value = response
                }
                is ExercicioRepositoryStatus.Error -> {
                    _error.value = response
                }
                else -> {
                    _carregar.value = true
                }
            }
        }
    }

    fun setTreino(treino: Treino) = viewModelScope.launch {
        treinoRepository.setTreino(treino).apply {
            when (this) {
                is TreinoRepositoryStatus.SetTreinoSuccess -> {
                    _currentMsg.value = response
                    _finish.value = true
                }
                is TreinoRepositoryStatus.Error -> {
                    _error.value = response
                }
                is TreinoRepositoryStatus.SetTreinoResponse ->{
                    _currentMsg.value = response
                }
                else -> {

                }
            }
        }
    }
}