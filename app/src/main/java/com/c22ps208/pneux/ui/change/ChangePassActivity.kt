package com.c22ps208.pneux.ui.change

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.ActivityChangeEmailBinding
import com.c22ps208.pneux.databinding.ActivityChangePassBinding
import com.c22ps208.pneux.ui.login.LoginActivity
import com.c22ps208.pneux.ui.navigation.account.AccountFragment
import com.google.firebase.auth.FirebaseAuth

class ChangePassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePassBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        btnBack()

        binding.save.setOnClickListener {
            val newPassword = binding.newPass.text.toString()
            val confirmNewPassword = binding.newwPass.text.toString()

            if (newPassword.isEmpty()) {
                binding.newPass.error = getString(R.string.validation_passwd)
                binding.newPass.requestFocus()
                return@setOnClickListener

            }
            if (confirmNewPassword.isEmpty()) {
                binding.newwPass.error = getString(R.string.validation_passwd)
                binding.newwPass.requestFocus()
                return@setOnClickListener

            }
            if (newPassword != confirmNewPassword) {
                binding.newwPass.error = getString(R.string.validation_password)
                binding.newwPass.requestFocus()
                return@setOnClickListener
            }
            if (newPassword.length < 8) {
                binding.newPass.error = getString(R.string.validation_passwd_length)
                binding.newPass.requestFocus()
                return@setOnClickListener

            }

            user.let {
                it?.updatePassword(newPassword)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Password Berhasil di Ubah", Toast.LENGTH_SHORT).show()
                        logOut()
                    }

                }
            }
        }
    }

    private fun logOut() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, AccountFragment::class.java))
        }
    }
}

