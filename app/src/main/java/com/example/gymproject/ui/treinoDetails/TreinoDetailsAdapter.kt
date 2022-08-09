package com.example.gymproject.ui.treinoDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymproject.databinding.TreinoDetailsItemBinding
import com.example.gymproject.model.Exercicio

class TreinoDetailsAdapter(val exercicios : List<Exercicio>) : RecyclerView.Adapter<TreinoDetailsAdapter.ViewHolder>() {

    class ViewHolder(val binding : TreinoDetailsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TreinoDetailsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TreinoDetailsAdapter.ViewHolder, position: Int) {
        with(holder){
            binding.tvNome.text = exercicios[position].nome
            binding.tvObservacoes.text = exercicios[position].observacoes
            Glide.with(binding.ivExercicioImg)
                .load(exercicios[position].imagem)
                .fitCenter()
                .into(binding.ivExercicioImg)
        }
    }

    override fun getItemCount(): Int {
        return exercicios.size
    }

}