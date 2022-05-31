package com.c22ps208.pneux.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityRegisterBinding
import com.c22ps208.pneux.ui.login.LoginActivity
import com.c22ps208.pneux.ui.start.OnBoardingActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        btnBackListener()
        btnRegisListener()

        btHaveListener()
        showLoading(true)

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun btHaveListener() {
        binding.tvYes.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun btnRegisListener() {
        binding.btRegis.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun btnBackListener() {
        binding.btBack2.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }
}