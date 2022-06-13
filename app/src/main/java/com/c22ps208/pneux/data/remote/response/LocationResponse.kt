package com.example.pneux_mobile.data.model

import com.google.gson.annotations.SerializedName

class LocationResponse {

    @SerializedName("location")
    lateinit var modelLocationResponse: ModelLocationResponse
}