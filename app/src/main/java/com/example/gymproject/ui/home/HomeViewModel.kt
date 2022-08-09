package com.example.gymproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymproject.model.Treino
import com.example.gymproject.repository.treino.TreinoRepository
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TreinoRepository) : ViewModel() {

    private val _treino = MutableLiveData<List<Treino>>()
    val treino: LiveData<List<Treino>>
        get() = _treino

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _carregar = MutableLiveData<Boolean>()
    val carregar: LiveData<Boolean>
        get() = _carregar

    fun getTreino(){
        repository.getTreino().apply {
            when(this){
                is TreinoRepositoryStatus.GetTreinoSuccess -> {
                    _treino.value = response
                }
                is TreinoRepositoryStatus.Error -> {
                    _error.value = response
                }
                else -> {
                    _carregar.value = true
                }
            }
        }
    }
}