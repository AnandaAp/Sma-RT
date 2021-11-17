package com.maluku.sma_rt.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.ui.NavigationUI
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySecondBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}