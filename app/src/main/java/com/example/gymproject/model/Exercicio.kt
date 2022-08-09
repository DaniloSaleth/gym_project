package com.example.gymproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Exercicio (
    val nome : String,
    val imagem : String,
    val observacoes : String
        ) : Parcelable