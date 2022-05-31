package com.c22ps208.pneux.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityLoginBinding
import com.c22ps208.pneux.ui.register.RegisterActivity
import com.c22ps208.pneux.ui.start.OnBoardingActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        btBackListener()
        btLoginListener()

        moveRegister()

    }

    private fun moveRegister() {
        binding.tvNo.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun btLoginListener() {
        binding.btLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun btBackListener() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }
}