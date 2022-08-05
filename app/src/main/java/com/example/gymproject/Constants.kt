package com.example.gymproject

import com.example.gymproject.model.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore

object Constants {
    val db = FirebaseFirestore.getInstance()
    val USER_DATA = UserFirebase()
}