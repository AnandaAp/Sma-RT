package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananDiprosesBinding
import com.maluku.sma_rt.databinding.FragmentPesananPenjualPageBinding
import com.maluku.sma_rt.view.onboarding.ViewPagerAdapter
import com.maluku.sma_rt.view.warga.adapter.ViewPagerPesanan

class PesananPenjualPage : Fragment() {

    private lateinit var binding: FragmentPesananPenjualPageBinding
    private lateinit var viewPagerAdapter: ViewPagerPesanan


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPesananPenjualPageBinding.inflate(layoutInflater)


        viewPagerAdapter = ViewPagerPesanan(childFragmentManager, lifecycle)

        with(binding){
            viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) {tab, position ->
                when (position){
                    0 -> tab.text = "Pesanan Masuk"
                    1 -> tab.text = "Diproses"
                    2 -> tab.text = "Selesai"
                }
            }.attach()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananPenjualPage_to_kelolaToko)
        }

        return binding.root
    }

}