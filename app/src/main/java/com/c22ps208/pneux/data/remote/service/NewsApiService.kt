package com.c22ps208.pneux.data.remote.service

import com.c22ps208.pneux.BuildConfig
import com.c22ps208.pneux.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface NewsApiService {
    @GET("top-headlines?country=id&category=health&apiKey=$API_TOKEN")
    @Headers("Authorization: token $API_TOKEN", "UserResponse-Agent: request")
    fun getListNews() : Call<NewsResponse>

    companion object {
        private const val API_TOKEN = BuildConfig.NEWSAPI_TOKEN
    }
}