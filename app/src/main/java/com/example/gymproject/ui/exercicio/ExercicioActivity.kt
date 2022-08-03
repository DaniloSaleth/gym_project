package com.example.gymproject.ui.exercicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymproject.databinding.ActivityExerciciosBinding

class ExercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciciosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciciosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

    }
}