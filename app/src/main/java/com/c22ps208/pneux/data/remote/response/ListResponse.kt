package com.example.pneux_mobile.data.model

import com.google.gson.annotations.SerializedName

class ListResponse {

    @SerializedName("results")
    lateinit var hospitalResponse: List<HospitalResponse>
}