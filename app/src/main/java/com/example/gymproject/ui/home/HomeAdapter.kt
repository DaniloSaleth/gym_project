package com.example.gymproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymproject.databinding.TreinoItemBinding
import com.example.gymproject.model.Treino

class HomeAdapter(private val treinos : List<Treino>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(val binding : TreinoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TreinoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.tvName.text = treinos[position].nome
        }
    }

    override fun getItemCount(): Int {
        return treinos.size
    }

}