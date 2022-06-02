package com.c22ps208.pneux.ui.navigation.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}