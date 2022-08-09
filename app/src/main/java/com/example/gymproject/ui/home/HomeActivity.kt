package com.example.gymproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymproject.databinding.ActivityHomeBinding
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.treino.TreinoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel : HomeViewModel by viewModel()

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAddTreino.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    TreinoActivity::class.java
                )
            )
        }

        binding.btnCarregar.setOnClickListener {
            viewModel.getTreino()
        }

        startObserver()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTreino()
    }

    private fun startObserver(){
        viewModel.treino.observe(this){
            binding.rvTreino.visibility = View.VISIBLE
            binding.btnCarregar.visibility = View.GONE
            setupRecyclerView(it)
        }

        viewModel.error.observe(this){
            if (it.message != null) {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG)
            }
        }

        viewModel.carregar.observe(this){
            binding.rvTreino.visibility = View.INVISIBLE
            binding.btnCarregar.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(treinos: List<Treino>){
            binding.tvResultados.text = "${treinos.size} resultados"
            adapter = HomeAdapter(treinos)
            binding.rvTreino.layoutManager = LinearLayoutManager(this,1,false)
            binding.rvTreino.adapter = adapter
    }

    private fun openTreino(){
        adapter.openTreinoDetail {
           /* val bundle = Bundle()
            val intent = Intent(binding.root.context, MovieDetailActivity::class.java)
                .putExtra("treino", it)
            ContextCompat.startActivity(binding.root.context, intent, bundle)*/
        }
    }
}