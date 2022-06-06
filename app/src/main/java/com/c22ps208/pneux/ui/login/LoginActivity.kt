package com.c22ps208.pneux.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityLoginBinding
import com.c22ps208.pneux.ui.password.ForgotPasswordActivity
import com.c22ps208.pneux.ui.register.RegisterActivity
import com.c22ps208.pneux.ui.start.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        btBackListener()
        btForgetListener()
        btNoListener()

        binding.btLogin.setOnClickListener {
            val email = binding.textEmail.text.toString()
            val password = binding.textPass.text.toString()

            if (email.isEmpty()) {
                binding.textEmail.error = "email harus di isi"
                binding.textEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.textEmail.error = "Email yang di masukkan tidak valid"
                binding.textEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.textPass.error = "password tidak boleh kosong"
                binding.textPass.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.textPass.error = "password minimal 6 karakter"
                binding.textPass.requestFocus()
                return@setOnClickListener

            }
            loginFirebase(email, password)
            showLoading(false)

        }

    }


    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Selamat Datang $email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }

            }
    }

    private fun btNoListener() {
        binding.tvNo.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btForgetListener() {
        binding.tvForget.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))

        }
    }

    private fun btBackListener() {
        binding.btnBackLogin.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}