package com.example.gymproject.ui.exercicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gymproject.databinding.ActivityExerciciosBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.ui.treino.TreinoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciciosBinding

    private val viewModel: ExercicioViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciciosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val exercicio = Exercicio(
                binding.ieName.text.toString(),
                "teste",
                binding.ieDescription.text.toString()
            )
            viewModel.setExercicio(exercicio)
        }

        startObserver()
    }

    fun startObserver(){
        viewModel.currentMsg.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
            finish()
        }
    }
}