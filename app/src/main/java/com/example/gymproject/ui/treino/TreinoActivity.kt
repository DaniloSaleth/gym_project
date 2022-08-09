package com.example.gymproject.ui.treino

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymproject.databinding.ActivityTreinoBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.exercicio.ExercicioActivity
import com.example.gymproject.ui.home.HomeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate

class TreinoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoBinding
    private lateinit var adapter: TreinoAdapter

    private val viewModel: TreinoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreinoBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        binding.rvExercicio.layoutManager = LinearLayoutManager(this, 1, false)
        binding.rvExercicio.adapter = adapter

        addExercicio()
        removeExercicio()
    }

    private fun startListener(){
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
                LocalDate.now().toString(),
                viewModel.exerciciosToAdd.value!!
            )
            viewModel.setTreino(treino)
        }

        binding.btnCarregar.setOnClickListener {
            viewModel.getExercicio()
        }
    }

    private fun addExercicio(){
        adapter.addExercicioTreino {
            viewModel.addToTreino(it)
        }
    }

    private fun removeExercicio(){
        adapter.removeExercicioTreino {
            viewModel.removeToTreino(it)
        }
    }

    private fun startObserver() {
        viewModel.exercicio.observe(this) {
            binding.rvExercicio.visibility = View.VISIBLE
            binding.btnCarregar.visibility = View.GONE
            setupRecyclerView(it)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG)
        }

        viewModel.currentMsg.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
            finish()
        }

        viewModel.carregar.observe(this){
            binding.rvExercicio.visibility = View.INVISIBLE
            binding.btnCarregar.visibility = View.VISIBLE
        }
    }
}