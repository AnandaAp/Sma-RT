package com.maluku.sma_rt.view.onboarding.screens


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentSecondScreenBinding
import com.maluku.sma_rt.view.activity.DashboardWargaActivity

class SecondScreen : Fragment() {

    private lateinit var binding: FragmentSecondScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_second_screen, container, false)
        binding = FragmentSecondScreenBinding.inflate(layoutInflater)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.btnNext2.setOnClickListener{
            viewPager?.currentItem = 2
        }

        binding.skip.setOnClickListener{
            val intent = Intent (activity, DashboardWargaActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}