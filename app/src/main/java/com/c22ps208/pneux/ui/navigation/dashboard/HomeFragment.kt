package com.c22ps208.pneux.ui.navigation.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.FragmentHomeBinding
import com.c22ps208.pneux.ui.navigation.history.HistoryActivity
import com.c22ps208.pneux.ui.navigation.scan.ScanActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        displayName()
        // CardView Listener
        cardViewListener()
        bannerListener()
    }

    private fun bannerListener() {
        binding.btnBannerNews.setOnClickListener() {
            val intent = Intent(context, NewsActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private fun cardViewListener() {
        binding.cvHos.setOnClickListener() {
            // TO DO
        }

        binding.cvNews.setOnClickListener() {
            val intent = Intent(context, NewsActivity::class.java)
            context?.startActivity(intent)
        }

        binding.cvScan.setOnClickListener() {
            val intent = Intent(context, ScanActivity::class.java)
            context?.startActivity(intent)
        }

        binding.cvHistory.setOnClickListener() {
            val intent = Intent(context, HistoryActivity::class.java)
            context?.startActivity(intent)
        }
    }

    private fun displayName() {
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.tvUsername.text = snapshot.child("username").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}