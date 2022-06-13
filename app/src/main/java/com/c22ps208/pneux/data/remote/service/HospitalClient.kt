package com.example.pneux_mobile.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HospitalClient {

    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/maps/api/place/"
        fun getClient(): HospitalInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(HospitalInterface::class.java)
        }
    }
}