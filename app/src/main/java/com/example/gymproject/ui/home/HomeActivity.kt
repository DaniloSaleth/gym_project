package com.example.gymproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gymproject.databinding.ActivityHomeBinding
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.treino.TreinoActivity
import com.example.gymproject.ui.treinoDetails.TreinoDetailsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListener()
        startObserver()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getTreino()
    }

    private fun setupListener(){
        binding.btnAddTreino.setOnClickListener {
            var intent = Intent(this,TreinoActivity::class.java)
                .putExtra("editTreino", false)
            startActivity(intent)
        }

        binding.ieSearchTreino.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                if (currentFocus != null) {
                    val inputMethodManager: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                }
                viewModel.getTreinoByName(textView.text.toString())
                true
            }
            false
        }
    }

    private fun startObserver() {
        viewModel.treino.observe(this) {
            setupRecyclerView(it)
        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG)
        }
    }

    private fun setupRecyclerView(treinos: List<Treino>) {
        binding.tvResultados.text = "${treinos.size} resultados"
        adapter = HomeAdapter(treinos)
        binding.rvTreino.layoutManager = LinearLayoutManager(this, 1, false)
        binding.rvTreino.adapter = adapter

        openTreino()
    }

    private fun openTreino() {
        adapter.openTreinoDetail {
            val bundle = Bundle()
            val intent = Intent(binding.root.context, TreinoDetailsActivity::class.java)
                .putExtra("treino", it)
            ContextCompat.startActivity(binding.root.context, intent, bundle)
        }
    }
}