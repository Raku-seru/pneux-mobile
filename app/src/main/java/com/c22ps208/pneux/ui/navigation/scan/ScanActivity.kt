package com.c22ps208.pneux.ui.navigation.scan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityScanBinding
import com.c22ps208.pneux.ui.password.ForgotPasswordActivity

class ScanActivity : AppCompatActivity() {
    private lateinit var binding : ActivityScanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        btnBack()
    }

    private fun btnBack() {
        binding.btnBackHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}