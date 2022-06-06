package com.c22ps208.pneux.ui.navigation.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}