package com.example.pneux_mobile.data.model

import com.c22ps208.pneux.data.remote.response.HospitalResponse
import com.google.gson.annotations.SerializedName

class ListResponse {

    @SerializedName("results")
    lateinit var hospitalResponse: List<HospitalResponse>
}