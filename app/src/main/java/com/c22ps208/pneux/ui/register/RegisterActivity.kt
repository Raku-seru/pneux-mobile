package com.c22ps208.pneux.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityRegisterBinding
import com.c22ps208.pneux.ui.login.LoginActivity
import com.c22ps208.pneux.ui.start.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        btYesListener()

        binding.btRegis.setOnClickListener {
            val username = binding.textUser.text.toString()
            val email = binding.textEmail.text.toString()
            val password = binding.textPass.text.toString()

            if (username.isEmpty()) {
                binding.textUser.error = "isi nama terlebih dahulu"
                binding.textUser.requestFocus()
                return@setOnClickListener
            }
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
            if (password.length < 8) {
                binding.textPass.error = "password kurang dari 8"
                binding.textPass.requestFocus()
                return@setOnClickListener

            }
            registerFirebase(email, password)
            showLoading(false)
        }
    }


    private fun registerFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Selamat akun anda suda terdaftar di PneuX",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun btYesListener() {
        binding.tvYes.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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