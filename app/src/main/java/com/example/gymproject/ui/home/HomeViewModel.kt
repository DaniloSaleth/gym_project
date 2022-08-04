package com.example.gymproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymproject.model.Treino
import com.example.gymproject.repository.treino.TreinoRepositoryImpl
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TreinoRepositoryImpl) : ViewModel() {

    private val _treino = MutableLiveData<List<Treino>>()
    val treino: LiveData<List<Treino>>
        get() = _treino

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    fun getTreino() = viewModelScope.launch{
        repository.getTreino().apply {
            when(this){
                is TreinoRepositoryStatus.GetTreinoSuccess -> {
                    _treino.value = response
                }
                is TreinoRepositoryStatus.Error -> {
                    _error.value = response
                }
                else -> {

                }
            }
        }
    }
}