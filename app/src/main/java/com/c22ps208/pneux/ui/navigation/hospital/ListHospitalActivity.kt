package com.c22ps208.pneux.ui.navigation.hospital

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.c22ps208.pneux.R
import com.c22ps208.pneux.adapter.HospitalAdapter
import com.c22ps208.pneux.ui.viewmodels.HospitalViewModel
import com.example.pneux_mobile.data.model.HospitalResponse
import im.delight.android.location.SimpleLocation
import java.util.ArrayList

class ListHospitalActivity : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog
    lateinit var simpleLocation: SimpleLocation
    lateinit var strLokasi: String
    lateinit var hospitalAdapter: HospitalAdapter
    lateinit var hospitalViewModel: HospitalViewModel
    var strLatitude = 0.0
    var strLongitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_hospital)

        setActionBar()


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Mohon tunggu...")
        progressDialog.setCancelable(false)
        progressDialog.setMessage("sedang menampilkan lokasi")

        //set library location
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        //get location
        strLatitude = simpleLocation.latitude
        strLongitude = simpleLocation.longitude

        //set location lat long
        strLokasi = "$strLatitude,$strLongitude"


        //set data adapter
        hospitalAdapter = HospitalAdapter(this)
        rvListHospital.setLayoutManager(LinearLayoutManager(this))
        rvListHospital.setAdapter(hospitalAdapter)
        rrvListHospital.setHasFixedSize(true)

        //viewmodel
        hospitalViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HospitalViewModel::class.java)
        hospitalViewModel.setHospitalLocation(strLokasi)
        show()
        hospitalViewModel.getHospitalLocation().observe(this, { hospitalResponse: ArrayList<HospitalResponse?> ->
            if (hospitalResponse.size != 0) {
                hospitalAdapter.setResultAdapter(hospitalResponse)
                progressDialog.dismiss()
            } else {
                Toast.makeText(this, "Oops, lokasi Rumah Sakit tidak ditemukan!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setActionBar() {
            supportActionBar?.title = "Rumah Sakit Terdekat"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}