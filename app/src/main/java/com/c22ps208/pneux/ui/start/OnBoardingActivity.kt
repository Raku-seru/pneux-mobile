package com.c22ps208.pneux.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityOnBoarding2Binding
import com.c22ps208.pneux.ui.login.LoginActivity
import com.c22ps208.pneux.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoarding2Binding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        val currentuser = auth.currentUser
        if (currentuser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnCreatedListener()
    }

    private fun btnCreatedListener() {
        binding.btnCreated.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}