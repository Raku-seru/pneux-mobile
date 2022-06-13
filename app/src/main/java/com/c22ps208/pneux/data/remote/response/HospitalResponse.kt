package com.c22ps208.pneux.data.remote.response

import android.os.Parcelable
import com.example.pneux_mobile.data.model.LocationResponse
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class HospitalResponse(

    @SerializedName("geometry")
    val locationResponse: LocationResponse,

    @SerializedName("name")
    val name: String,

    @SerializedName("vicinity")
    val vicinity: String,

    @SerializedName("place_id")
    val placeId: String
) : Parcelable