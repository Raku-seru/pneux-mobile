package com.c22ps208.pneux.ui.navigation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityResultBinding
import kotlin.math.roundToInt

class ResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val acc = (intent.getFloatExtra("EXTRA_PROB",5.0f) * 1000.0).roundToInt()
        var newAcc = 100 - acc
        if (newAcc < 0) newAcc = 0
        binding.tvResultAcc.text = "$newAcc%"

        binding.tvResultHeader.text = intent.getStringExtra("EXTRA_RESULT").toString()

        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnResultFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}