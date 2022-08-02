package com.example.gymproject

import com.example.gymproject.model.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val TOKEN = "?api_key=b81e9114b2a9b619527c94e4536cfb0a"
    val db = FirebaseFirestore.getInstance()
    val USER_DATA = UserFirebase()
}