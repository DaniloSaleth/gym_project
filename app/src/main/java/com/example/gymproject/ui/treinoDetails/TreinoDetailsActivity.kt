package com.example.gymproject.ui.treinoDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymproject.databinding.ActivityTreinoDetailsBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TreinoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTreinoDetailsBinding
    private lateinit var adapter: TreinoDetailsAdapter

    private val viewModel: TreinoDetailsViewModel by viewModel()

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
        binding.btnDeletarTreino.setOnClickListener {
            viewModel.removeTreino(treino)
        }

        startObserver()
    }

    private fun startObserver(){
        viewModel.error.observe(this){
            Log.v("teste", ""+it.message)
        }

        viewModel.msg.observe(this){
            finish()
        }
    }

    private fun setupRecyclerView(exercicios: List<Exercicio>) {
        adapter = TreinoDetailsAdapter(exercicios)
        binding.rvExercicio.layoutManager = LinearLayoutManager(this, 1, false)
        binding.rvExercicio.adapter = adapter
    }
}