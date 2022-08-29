package com.example.gymproject.ui.exercicio

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gymproject.databinding.ActivityExerciciosBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.ui.treino.TreinoViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class ExercicioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciciosBinding
    private lateinit var imageUri: Uri
    private lateinit var fileName: String

    private val viewModel: ExercicioViewModel by viewModel()
    var imageChanged = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityExerciciosBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        startListner()
        startObserver()
    }

    private fun startListner() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            val formatter = SimpleDateFormat("YYY_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            fileName = formatter.format(now)
            val exercicio = Exercicio(
                0,
                binding.ieName.text.toString(),
                "https://firebasestorage.googleapis.com/v0/b/gym-p-bba6d.appspot.com/o/images%2F$fileName?alt=media",
                binding.ieDescription.text.toString()
            )
            viewModel.setExercicio(exercicio)
        }

        binding.ivExercicio.setOnClickListener {
            selectImage()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    private fun uploadImage(fileName: String) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Salvando imagem ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(this, "Salvo com sucesso", Toast.LENGTH_SHORT)

            if (progressDialog.isShowing){
                progressDialog.dismiss()
                finish()
            }
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
            Toast.makeText(this, "Erro ao salva", Toast.LENGTH_SHORT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            imageUri = data?.data!!
            binding.ivExercicio.setImageURI(imageUri)
            imageChanged = true
        }
    }

    private fun startObserver() {
        viewModel.currentMsg.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT)
            if (imageChanged) {
                uploadImage(fileName)
            }else {
                finish()
            }
        }
    }
}