package com.c22ps208.pneux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.c22ps208.pneux.databinding.ActivityMainBinding
import com.c22ps208.pneux.ui.navigation.account.AccountFragment
import com.c22ps208.pneux.ui.navigation.dashboard.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavView: BottomNavigationView
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        bottomNavView = binding.bottomNavView

        val homeFragment = HomeFragment()
        val accountFragment = AccountFragment()

        setThatFragments(homeFragment)

        bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    setThatFragments(homeFragment)
                }
                R.id.navigation_account -> {
                    setThatFragments(accountFragment)
                }

            }

            true
        }

    }

    private fun setThatFragments(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame, fragment)
            commit()
        }
    }
}