package com.example.gymproject

import android.app.Application
import com.example.gymproject.di.fireBase
import com.example.gymproject.di.repository
import com.example.gymproject.di.viewModels
import com.example.gymproject.model.UserFirebase
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            if(BuildConfig.DEBUG){
                androidLogger()
            }
            androidContext(this@MyApp)
            modules(viewModels, repository, fireBase)
        }
    }
}