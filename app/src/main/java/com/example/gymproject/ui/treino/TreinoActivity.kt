package com.example.gymproject.ui.treino

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gymproject.databinding.ActivityTreinoBinding
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.exercicio.ExercicioActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class TreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoBinding
    private val viewModel: TreinoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startObserver()

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

        binding.btnSave.setOnClickListener {
            var treino = Treino(binding.ieName.text.toString(),
                binding.ieDescription.text.toString(),
                LocalDate.now().toString()
            )

            viewModel.setTreino(treino)
        }

    }

    private fun startObserver(){
        viewModel.error.observe(this){
            Toast.makeText(this,it.message,Toast.LENGTH_LONG)
        }

        viewModel.currentMsg.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT)
        }
    }
}