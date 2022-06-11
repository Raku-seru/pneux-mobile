package com.c22ps208.pneux.ui.navigation.dashboard

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps208.pneux.adapter.NewsAdapter
import com.c22ps208.pneux.adapter.OnItemClickCallback
import com.c22ps208.pneux.data.remote.response.ArticlesItem
import com.c22ps208.pneux.databinding.ActivityNewsBinding
import com.c22ps208.pneux.preferences.SettingPreferences
import com.c22ps208.pneux.ui.viewmodels.NewsViewModel
import com.c22ps208.pneux.ui.viewmodels.ViewModelFactory

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")

class NewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBinding
    private lateinit var newsViewModel: NewsViewModel
    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        setupViewModel()
        setupNewsList()

        // Theme Mode
        newsViewModel.getThemeSetting().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupViewModel() {
        newsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(SettingPreferences.getInstance(dataStore))
        )[NewsViewModel::class.java]
    }

    private fun setActionBar() {
        supportActionBar?.title = "News"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupNewsList() {
        showFailedLoadData(false)
        showProgressBar(true)
        newsViewModel.news.observe(this) { newsRes ->
            if(newsRes != null) {
                showProgressBar(false)
                newsAdapter.addDataToList(newsRes)
                setUserAdapter()
            } else {
                showProgressBar(false)
                showFailedLoadData(true)
                Toast.makeText(
                    this@NewsActivity,
                    "Failed to Load Data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setUserAdapter() {
        val layoutManager =
            LinearLayoutManager(this@NewsActivity, LinearLayoutManager.VERTICAL, false)
        binding.rvNewslist.layoutManager = layoutManager
        binding.rvNewslist.adapter = newsAdapter
        binding.rvNewslist.setHasFixedSize(true)

        newsAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(articlesItem: ArticlesItem) {
                val uri: Uri =
                    Uri.parse(articlesItem.url) // missing 'http://' will cause crashed

                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Loading bar controller
    private fun showProgressBar(isLoading: Boolean) {
        binding.pbNewsLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    // Failed text controller
    private fun showFailedLoadData(isFailed: Boolean) {
        binding.tvNewsFailed.visibility = if (isFailed) View.VISIBLE else View.GONE
    }
}