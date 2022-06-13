package com.c22ps208.pneux.adapter


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.ItemHospitalBinding
import com.c22ps208.pneux.databinding.ItemListNewsBinding
import com.example.pneux_mobile.data.local.jarak.getDistance
import com.example.pneux_mobile.data.model.HospitalResponse
import im.delight.android.location.SimpleLocation
import java.text.DecimalFormat
import java.util.ArrayList


class HospitalAdapter (private val context: Context?) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    private val hospitalResponse = ArrayList<HospitalResponse>()
    lateinit var simpleLocation: SimpleLocation
    var strLatitude = 0.0
    var strLongitude = 0.0

    fun setResultAdapter(items: ArrayList<HospitalResponse>) {
        hospitalResponse.clear()
        hospitalResponse.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_list_hospital, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val item = hospitalResponse[position]

        simpleLocation = SimpleLocation(context)

        //current location
        strLatitude = simpleLocation.latitude
        strLongitude = simpleLocation.longitude
        val strPlaceID = hospitalResponse[position].placeId

        //location destination
        val strLat = hospitalResponse[position].locationResponse.modelLocationResponse.lat
        val strLong = hospitalResponse[position].locationResponse.modelLocationResponse.lng


        holder.nameRs.text = item.name
        holder.alamatRs.text = item.vicinity


        holder.rvHospital.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=$strLat,$strLong")
            )
            startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return hospitalResponse.size
    }

    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvHospital: RelativeLayout
        val nameRs: TextView
        val alamatRs: TextView


        init {
            rvHospital = itemView.rvHospital
            nameRs = itemView.nameRs
            alamatRs = itemView.alamatRs

        }
    }
}
