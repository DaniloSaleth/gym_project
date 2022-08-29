package com.example.gymproject.ui.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymproject.Constants
import com.example.gymproject.model.FirebaseData
import com.example.gymproject.model.Treino
import com.example.gymproject.repository.treino.TreinoRepository
import com.example.gymproject.repository.treino.TreinoRepositoryStatus
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: TreinoRepository) : ViewModel() {

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

    fun getTreinoByName(name : String) = viewModelScope.launch{
        repository.getTreinoByName(name).apply {
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