package com.example.gymproject.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gymproject.databinding.ActivityHomeBinding
import com.example.gymproject.ui.treino.TreinoActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

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

    }
}