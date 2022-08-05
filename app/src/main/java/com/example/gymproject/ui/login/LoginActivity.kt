package com.example.gymproject.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.gymproject.Constants.USER_DATA
import com.example.gymproject.databinding.ActivityLoginBinding
import com.example.gymproject.ui.home.HomeActivity
import com.example.gymproject.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {
            finish()
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener{
            when{
                TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim(){it<=' '}) ->{
                    Toast.makeText(this, "Please insert an email.", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim(){it<=' '}) ->{
                    Toast.makeText(this, "Please insert a Password.", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val email: String = binding.etLoginEmail.text.toString().trim{it <= ' '}
                    val password: String = binding.etLoginPassword.text.toString().trim{it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser = task.result.user!!
                                USER_DATA.user_id = firebaseUser.uid
                                USER_DATA.email_id = email
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