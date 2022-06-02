package com.c22ps208.pneux.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.databinding.ActivityOnBoarding2Binding
import com.c22ps208.pneux.ui.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoarding2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        btnCreatedListener()
    }

    private fun btnCreatedListener() {
        binding.btCreated.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}