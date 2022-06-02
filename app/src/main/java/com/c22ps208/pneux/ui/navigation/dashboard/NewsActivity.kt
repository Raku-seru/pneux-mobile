package com.c22ps208.pneux.ui.navigation.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c22ps208.pneux.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
    }

    private fun setActionBar() {
        supportActionBar?.title = "Berita"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}