package com.c22ps208.pneux.data.remote.service

import com.c22ps208.pneux.BuildConfig
import com.c22ps208.pneux.data.remote.response.NearbyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalInterface {
    @GET("nearbysearch/json")
    fun getNearbySearch(
        @Query("key") key: String,
        @Query("keyword") keyword: String? = "hospital",
        @Query("location") location: String? = "-6.174283898481838,106.82656373164592",
        @Query("rankby") rankby: String
    ): Call<NearbyResponse>

}