package com.example.gymproject.ui.treino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymproject.databinding.ActivityTreinoBinding
import com.example.gymproject.ui.exercicio.ExercicioActivity

class TreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnAddExercise.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    ExercicioActivity::class.java
                )
            )
        }

    }
}