package com.c22ps208.pneux.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.c22ps208.pneux.MainActivity
import com.c22ps208.pneux.databinding.ActivityRegisterBinding
import com.c22ps208.pneux.ui.login.LoginActivity
import com.c22ps208.pneux.ui.start.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        btYesListener()

        binding.btRegis.setOnClickListener {
            val username = binding.textUser.text.toString()
            val email = binding.textEmail.text.toString()
            val password = binding.textPassword.text.toString()

            if (username.isEmpty()) {
                binding.textUser.error = "fill in username"
                binding.textUser.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                binding.textEmail.error = "email must be filled"
                binding.textEmail.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.textEmail.error = "invalid email"
                binding.textEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.textPassword.error = "password cannot be empty"
                binding.textPassword.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 8) {
                binding.textPassword.error = "password less than 8"
                binding.textPassword.requestFocus()
                return@setOnClickListener

            }
            registerFirebase(email, password)
            showLoading(false)
        }
    }


    private fun registerFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val currentUser = auth.currentUser
                    val CurrentUserDb =databaseReference?.child(currentUser?.uid!!)
                    CurrentUserDb?.child("username")?.setValue(binding.textUser.text.toString())
                    Toast.makeText(
                        this,
                        "Welcome to PneuX",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun btYesListener() {
        binding.tvYes.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}