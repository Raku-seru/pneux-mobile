package com.c22ps208.pneux.ui.navigation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.FragmentAccountBinding

class AccountFragment : Fragment(R.layout.fragment_account) {

   private lateinit var binding: FragmentAccountBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountBinding.bind(view)

    }


}