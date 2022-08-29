package com.example.gymproject.ui.treino

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymproject.databinding.ActivityTreinoBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.exercicio.ExercicioActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class TreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoBinding
    private lateinit var adapter: TreinoAdapter
    private lateinit var treino : Treino

    private var editTreino = false

    private val viewModel: TreinoViewModel by viewModel()
    private var isAdapterOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTreino = intent.getBooleanExtra("editTreino", false)

        if(editTreino){
            treino = intent.extras?.get("treino") as Treino
            binding.ieName.setText(treino.nome)
            binding.ieDescription.setText(treino.descricao)
        }

        startListener()
        startObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getExercicio()
    }

    private fun setupRecyclerView(exercicios: List<Exercicio>) {
        binding.tvResultados.text = "${exercicios.size} resultados"
        adapter = TreinoAdapter(exercicios)
        isAdapterOn = true
        if (editTreino){
            adapter.mutableListExercicio = treino.exercicios
            viewModel.setListToTreino(treino.exercicios)
        }
        binding.rvExercicio.layoutManager = LinearLayoutManager(this, 1, false)
        binding.rvExercicio.adapter = adapter

        addExercicio()
        removeExercicio()
    }

    private fun startListener() {
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
            var newTreino = Treino(binding.ieName.text.toString(),
                binding.ieDescription.text.toString(),
                LocalDate.now().toString(),
                viewModel.exerciciosToAdd.value!!
            )
            if (editTreino){
                viewModel.updateTreino(newTreino, treino)
            }else {
                viewModel.setTreino(newTreino)
            }
        }
    }

    private fun addExercicio() {
        adapter.addExercicioTreino {
            viewModel.addToTreino(it)
        }
    }

    private fun removeExercicio() {
        adapter.removeExercicioTreino {
            viewModel.removeToTreino(it)
        }
    }

    private fun startObserver() {
        viewModel.exerciciosToAdd.observe(this) {
            if (isAdapterOn) {
                adapter.mutableListExercicio = it
            }
        }
        viewModel.exercicio.observe(this) {
            setupRecyclerView(it)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG)
        }

        viewModel.currentMsg.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
                .show()
        }

        viewModel.finish.observe(this) {
            finish()
        }
    }
}