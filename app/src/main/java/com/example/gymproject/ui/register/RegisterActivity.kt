package com.example.gymproject.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.gymproject.Constants
import com.example.gymproject.Constants.USER_DATA
import com.example.gymproject.Constants.db
import com.example.gymproject.databinding.ActivityRegisterBinding
import com.example.gymproject.model.Exercicio
import com.example.gymproject.model.Treino
import com.example.gymproject.ui.home.HomeActivity
import com.example.gymproject.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener{
            when{
                TextUtils.isEmpty(binding.etRegisterEmail.text.toString().trim(){it<=' '}) ->{
                    Toast.makeText(this, "Please enter email.", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.etRegisterPassword.text.toString().trim(){it<=' '}) ->{
                    Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show()
                }

                binding.etRegisterPassword.text.toString() != binding.etRegisterConfirmPassword.text.toString() ->{
                    Toast.makeText(this, "Password confirmation is different from password.", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val email: String = binding.etRegisterEmail.text.toString().trim{it <= ' '}
                    val password: String = binding.etRegisterPassword.text.toString().trim{it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result.user!!
                                Toast.makeText(this,
                                    "You are registered successfully.",
                                    Toast.LENGTH_SHORT)
                                    .show()

                                USER_DATA.user_id = firebaseUser.uid
                                USER_DATA.email_id = email

                                val data = hashMapOf(
                                    "treino" to emptyList<Treino>(),
                                    "exercicio" to emptyList<Exercicio>()
                                )
                                db.collection("user").document(USER_DATA.user_id).set(data)

                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                }
            }
        }

    }

}