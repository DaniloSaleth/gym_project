package com.example.gymproject.ui.treinoDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymproject.databinding.ActivityTreinoDetailsBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.Treino

class TreinoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoDetailsBinding
    private lateinit var adapter: TreinoDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreinoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val treino: Treino = intent.extras?.get("treino") as Treino

        binding.tvTitle.text = treino.nome
        binding.tvDescricao.text = treino.descricao
        setupRecyclerView(treino.exercicios)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView(exercicios: List<Exercicio>) {
        adapter = TreinoDetailsAdapter(exercicios)
        binding.rvExercicio.layoutManager = LinearLayoutManager(this, 1, false)
        binding.rvExercicio.adapter = adapter
    }
}