package com.c22ps208.pneux.ui.password

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.c22ps208.pneux.databinding.ActivityForgotPasswordBinding
import com.c22ps208.pneux.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        btBackListener()

        binding.btnNextForgot.setOnClickListener {
            val email: String = binding.etEmailForgot.text.toString().trim()

            if (email.isEmpty()) {
                binding.etEmailForgot.error = "email harus di isi"
                binding.etEmailForgot.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmailForgot.error = "Email yang di masukkan tidak valid"
                binding.etEmailForgot.requestFocus()
                return@setOnClickListener
            }
            forgotFirebase(email)
        }
    }

    private fun forgotFirebase(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                Toast.makeText(this, "cek email anda", Toast.LENGTH_SHORT).show()
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            } else {
                Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun btBackListener() {
        binding.btnBackPassword.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


}