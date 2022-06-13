package com.example.pneux_mobile.data.model

import com.google.gson.annotations.SerializedName

class HospitalResponse {

    @SerializedName("geometry")
    lateinit var  locationResponse: LocationResponse

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("vicinity")
    lateinit var vicinity: String

    @SerializedName("place_id")
    lateinit var placeId: String
}