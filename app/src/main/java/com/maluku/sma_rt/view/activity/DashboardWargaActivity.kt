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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        setContentView(R.layout.activity_dashboard_warga)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.fragment_dashboard_warga)


        bottomNav.setupWithNavController(navController)
        bottomNav.setItemIconTintList(null)


    }

}