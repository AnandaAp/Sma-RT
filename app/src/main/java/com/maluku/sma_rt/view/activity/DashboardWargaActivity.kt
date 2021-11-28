package com.maluku.sma_rt.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.ActivityDashboardWargaBinding

class DashboardWargaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardWargaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardWargaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav
        val navController = findNavController(R.id.fragment_dashboard_warga)

        navView.setupWithNavController(navController)
        navView.itemIconTintList = null
    }
}