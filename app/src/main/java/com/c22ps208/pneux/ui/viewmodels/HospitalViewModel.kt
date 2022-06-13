package com.c22ps208.pneux.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.c22ps208.pneux.BuildConfig
import com.c22ps208.pneux.data.remote.response.HospitalResponse
import com.c22ps208.pneux.data.remote.response.NearbyResponse
import com.c22ps208.pneux.data.remote.response.ResultsItem
import com.c22ps208.pneux.data.remote.service.ApiConfig
import com.example.pneux_mobile.data.model.ListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HospitalViewModel: ViewModel() {

    private val _nearby = MutableLiveData<NearbyResponse>()
    val nearby : LiveData<NearbyResponse> = _nearby

    private val _hospital = MutableLiveData<ArrayList<ResultsItem>>()
    val hospital : LiveData<ArrayList<ResultsItem>> = _hospital

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        viewModelScope.launch { setHospitalLocation("") }
        Log.i(TAG, "HospitalViewModel Created")
    }

    private fun setHospitalLocation(strLocation: String?) {
        coroutineScope.launch {
            val apiService = ApiConfig.getHospitalService()
            val call = apiService.getNearbySearch(mapApiKey, "hospital", strLocation, "distance")
            call.enqueue(object : Callback<NearbyResponse> {
                override fun onResponse(
                    call: Call<NearbyResponse>,
                    response: Response<NearbyResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "onResponse: $response")
                        val responseBody = response.body()
                        val results = responseBody?.results
                        if (results != null) {
                            _hospital.postValue(results)
                        }
                    }
                }

                override fun onFailure(call: Call<NearbyResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: Fail to fetch data", )
                }
            })
        }
    }

    companion object {
        private const val TAG = "NewsViewModel"
        val mapApiKey = BuildConfig.MAPS_API
    }
}