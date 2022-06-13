package com.example.pneux_mobile.data.api

import com.example.pneux_mobile.data.model.ListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalInterface {

    @GET("nearbysearch/json")
    fun getDataResult(@Query("key") key: String,
                      @Query("keyword") keyword: String,
                      @Query("location") location: String,
                      @Query("rankby") rankby: String?): Call<ListResponse>

}