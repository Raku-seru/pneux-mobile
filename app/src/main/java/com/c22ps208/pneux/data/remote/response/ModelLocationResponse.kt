package com.example.pneux_mobile.data.model

import com.google.gson.annotations.SerializedName

class ModelLocationResponse {

    @SerializedName("lat")
    var lat: Double = 0.0

    @SerializedName("lng")
    var lng: Double = 0.0
}