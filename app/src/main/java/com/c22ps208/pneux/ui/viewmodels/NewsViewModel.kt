package com.c22ps208.pneux.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.c22ps208.pneux.data.remote.response.ArticlesItem
import com.c22ps208.pneux.data.remote.response.NewsResponse
import com.c22ps208.pneux.data.remote.service.ApiConfig
import com.c22ps208.pneux.preferences.SettingPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(
    private val settingPref: SettingPreferences
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDataFailed = MutableLiveData<Boolean>()
    val isDataFailed: LiveData<Boolean> = _isDataFailed

    private val _news = MutableLiveData<List<ArticlesItem>?>()
    val news: LiveData<List<ArticlesItem>?> = _news

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    // Theme
    fun getThemeSetting(): LiveData<Boolean> {
        return settingPref.getThemeSetting().asLiveData()
    }

    init {
        viewModelScope.launch { getListNews() }
        Log.i(TAG, "NewsViewModel Created")
    }

    private fun getListNews() {
        coroutineScope.launch {
            _isLoading.value = true
            val client = ApiConfig.getNewsApiService().getListNews()
            client.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        _isLoading.value = false
                        val responseBody = response.body()
                        if (responseBody != null) {
                            if (responseBody.articles != null) {
                                _news.postValue(responseBody.articles)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    _isLoading.value = false
                    _isDataFailed.value = true
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}