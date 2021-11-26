package com.maluku.sma_rt.view.activity

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.ActivityDashboardRtBinding
import com.maluku.sma_rt.view.pengurus.adapter.GaleriAdapter

class DashboardRTActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardRtBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardRtBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard)

        navView.setupWithNavController(navController)
        navView.itemIconTintList = null

        // Action Bar
        /*
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_warga, R.id.navigation_manage,R.id.navigation_profil
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

         */
    }
}