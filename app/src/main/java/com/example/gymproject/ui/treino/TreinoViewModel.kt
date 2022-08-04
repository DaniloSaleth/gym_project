package com.example.gymproject.ui.treino

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

class TreinoViewModel(private val exercicioRepository: ExercicioRepository, private val treinoRepository: TreinoRepository): ViewModel() {

    private val _currentMsg = MutableLiveData<String>()
    val currentMsg: LiveData<String>
        get() = _currentMsg

    private val _exercicio = MutableLiveData<List<Exercicio>>()
    val exercicio: LiveData<List<Exercicio>>
        get() = _exercicio

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun getExercicio() = viewModelScope.launch{
        exercicioRepository.getExercicio().apply {
            when(this){
                is ExercicioRepositoryStatus.GetExercicioSuccess -> {
                    _exercicio.value = response
                }
                is ExercicioRepositoryStatus.Error -> {
                    _error.value = response
                }
                else -> {

                }
            }
        }
    }

    fun setTreino(treino: Treino) = viewModelScope.launch {
        treinoRepository.setTreino(treino).apply {
            when(this){
                is TreinoRepositoryStatus.SetTreinoSuccess ->{
                    _currentMsg.value = response
                }
                is TreinoRepositoryStatus.Error ->{
                    _error.value = response
                }
                else ->{

                }
            }
        }
    }
}