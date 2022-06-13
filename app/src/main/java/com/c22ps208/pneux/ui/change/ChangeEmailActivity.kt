package com.c22ps208.pneux.ui.change

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.R
import com.c22ps208.pneux.databinding.ActivityChangeEmailBinding
import com.c22ps208.pneux.ui.navigation.account.AccountFragment
import com.c22ps208.pneux.ui.start.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth

class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeEmailBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        btnBack()

        binding.save.setOnClickListener {
            val newEmail = binding.NewEmail.text.toString()

            if (newEmail.isEmpty()) {
                binding.NewEmail.error = getString(R.string.validation_email)
                binding.NewEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
                binding.NewEmail.error = getString(R.string.validation_emailvalid)
                binding.NewEmail.requestFocus()
                return@setOnClickListener
            }

            user?.let {
                user.updateEmail(newEmail).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Email Berhasil di Ubah", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, AccountFragment::class.java))
                         finish()
                    } else {
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }
    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}