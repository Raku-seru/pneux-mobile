package com.c22ps208.pneux.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pneux_mobile.data.api.HospitalClient
import com.example.pneux_mobile.data.model.HospitalResponse
import com.example.pneux_mobile.data.model.ListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HospitalViewModel: ViewModel() {

    companion object {
        val strApiKey = "YOUR API KEY"
    }

    private val hospitalResponseMutableLiveData = MutableLiveData<ArrayList<HospitalResponse>>()

    fun setHospitalLocation(strLocation: String) {
        val apiService = HospitalClient.getClient()
        val call = apiService.getDataResult(strApiKey, "hospital", strLocation, "distance")
        call.enqueue(object : Callback<ListResponse> {
            override fun onResponse(call: Call<ListResponse>, response: Response<ListResponse>) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    val items = ArrayList(response.body!!.hospitalResponse)
                    hospitalResponseMutableLiveData.postValue(items)
                }
            }

            override fun onFailure(call: Call<ListResponse>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }
    fun getHospitalLocation(): LiveData<ArrayList<HospitalResponse>> = hospitalResponseMutableLiveData
}