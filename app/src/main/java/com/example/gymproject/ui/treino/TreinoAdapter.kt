package com.example.gymproject.ui.treino

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gymproject.databinding.ExercicioItemBinding
import com.example.gymproject.model.Exercicio

class TreinoAdapter(private val exercicios: List<Exercicio>): RecyclerView.Adapter<TreinoAdapter.ViewHolder>() {

    private var addExercicio : ((Exercicio)->Unit)? = null
    private var removeExercicio : ((Exercicio)->Unit)? = null
    private var exercicioDetails : ((Exercicio)->Unit)? = null


    class ViewHolder(val binding : ExercicioItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ExercicioItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            binding.tvName.text = exercicios[position].nome
            Glide.with(binding.ivExercicio)
                .load(exercicios[position].imagem)
                .fitCenter()
                .into(binding.ivExercicio)

            binding.clExecicioItem.setOnClickListener {
                exercicioDetails?.let {
                    it(exercicios[position])
                }
            }

            binding.ivAddExercicio.setOnClickListener {
                binding.ivAddExercicio.setBackgroundColor(Color.GREEN)
                binding.ivRemoveExercicio.setBackgroundColor(Color.WHITE)
                addExercicio?.let {
                    it(exercicios[position])
                }
            }

            binding.ivRemoveExercicio.setOnClickListener {
                binding.ivAddExercicio.setBackgroundColor(Color.WHITE)
                binding.ivRemoveExercicio.setBackgroundColor(Color.RED)
                removeExercicio?.let {
                    it(exercicios[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return exercicios.size
    }

    fun addExercicioTreino(listener: (Exercicio) -> Unit){
        addExercicio = listener
    }

    fun removeExercicioTreino(listener: (Exercicio) -> Unit){
        removeExercicio = listener
    }

}