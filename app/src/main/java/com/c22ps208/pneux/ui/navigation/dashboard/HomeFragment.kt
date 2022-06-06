package com.c22ps208.pneux.ui.navigation.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.FragmentHomeBinding
import com.c22ps208.pneux.ui.navigation.hospital.HospitalActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.cdHos.setOnClickListener {
        val intent = Intent(this.requireContext(), HospitalActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}