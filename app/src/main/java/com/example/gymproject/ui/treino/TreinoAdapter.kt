package com.example.gymproject.ui.treino

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymproject.databinding.ExercicioItemBinding
import com.example.gymproject.model.Exercicio

class TreinoAdapter(private val exercicios: List<Exercicio>): RecyclerView.Adapter<TreinoAdapter.ViewHolder>() {

    class ViewHolder(val binding : ExercicioItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExercicioItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.tvName.text = exercicios[position].nome
        }
    }

    override fun getItemCount(): Int {
        return exercicios.size
    }

}