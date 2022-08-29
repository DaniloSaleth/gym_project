package com.example.gymproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Treino (
    var id : Int,
    val nome : String,
    val descricao : String,
    val data : String,
    val exercicios : List<Exercicio>
        ) : Parcelable