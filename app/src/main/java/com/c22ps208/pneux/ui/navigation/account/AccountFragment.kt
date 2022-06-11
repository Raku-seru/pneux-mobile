package com.c22ps208.pneux.ui.navigation.account

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.FragmentAccountBinding
import com.c22ps208.pneux.ui.change.ChangeEmailActivity
import com.c22ps208.pneux.ui.change.ChangePassActivity
import com.c22ps208.pneux.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AccountFragment : Fragment(R.layout.fragment_account) {

    private var _binding: FragmentAccountBinding? = null
    lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
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

        nameUser()

        binding.privasi.setOnClickListener {
            privacyDialog()
        }

        binding.aboutUs.setOnClickListener {
            aboutDialog()
        }


    }

    private fun aboutDialog() {
        val dialog = Dialog(this@AccountFragment.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.about_dialog)

        val btnOke = dialog.findViewById<Button>(R.id.btnOke)
        btnOke.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun privacyDialog() {
        val dialog = Dialog(this@AccountFragment.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.privacy_dialog)

        val btnOk = dialog.findViewById<Button>(R.id.btnOk)
        btnOk.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun nameUser() {
        val user = auth.currentUser
        val userReference = databaseReference?.child(user?.uid!!)

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.username.text = snapshot.child("username").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.logOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.changeEmail.setOnClickListener {
            val intent = Intent(activity, ChangeEmailActivity::class.java)
            startActivity(intent)
        }
        binding.changePass.setOnClickListener {
            val intent = Intent(activity, ChangePassActivity::class.java)
            startActivity(intent)
        }


    }
}

