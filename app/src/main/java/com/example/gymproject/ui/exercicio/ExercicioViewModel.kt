package com.example.gymproject.ui.exercicio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymproject.model.Exercicio
import com.example.gymproject.repository.exercicio.ExercicioRepository
import com.example.gymproject.repository.exercicio.ExercicioRepositoryStatus
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import kotlinx.coroutines.launch

class ExercicioViewModel(val exercicioRepository: ExercicioRepository) : ViewModel() {

    private val _currentMsg = MutableLiveData<String>()
    val currentMsg: LiveData<String>
        get() = _currentMsg

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun setExercicio(exercicio: Exercicio) = viewModelScope.launch {
        exercicioRepository.setExercicio(exercicio).apply {
            when (this) {
                is ExercicioRepositoryStatus.SetExercicioSuccess -> {
                    _currentMsg.value = response
                }
                is ExercicioRepositoryStatus.Error -> {
                    _error.value = response
                }
                else -> {

                }
            }
        }
    }
}