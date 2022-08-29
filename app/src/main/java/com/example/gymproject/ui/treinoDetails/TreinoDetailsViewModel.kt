package com.example.gymproject.ui.treinoDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymproject.model.Treino
import com.example.gymproject.repository.treino.TreinoRepository
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import kotlinx.coroutines.launch

class TreinoDetailsViewModel(val repository: TreinoRepository) : ViewModel() {

    private var _error = MutableLiveData<Throwable>()
    val error : LiveData<Throwable>
        get() = _error

    private var _msg = MutableLiveData<String>()
    val msg : LiveData<String>
        get() = _msg

    fun removeTreino(treino: Treino) = viewModelScope.launch{
        repository.removeTreino(treino).apply {
            when(this){
                is TreinoRepositoryStatus.Error -> {
                    _error.value = response
                }
                is TreinoRepositoryStatus.RemoveTreinoSuccess -> {
                    _msg.value = response
                }
                else -> {

                }
            }
        }
    }
}