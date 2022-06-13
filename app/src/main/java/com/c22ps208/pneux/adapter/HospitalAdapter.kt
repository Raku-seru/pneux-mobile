package com.c22ps208.pneux.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.c22ps208.pneux.R
import com.c22ps208.pneux.data.remote.response.HospitalResponse
import im.delight.android.location.SimpleLocation
import java.util.*
import kotlin.collections.ArrayList


class HospitalAdapter (private val context: Context?) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    private val hospitalResponse = ArrayList<HospitalResponse>()
    lateinit var simpleLocation: SimpleLocation
    var strLatitude = 0.0
    var strLongitude = 0.0

    @SuppressLint("NotifyDataSetChanged")
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
            val uri: String =
                java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", strLat, strLong)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//            val intent = Intent(
//                Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?daddr=$strLat,$strLong")
//            )
            context?.startActivity(intent)
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

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}
