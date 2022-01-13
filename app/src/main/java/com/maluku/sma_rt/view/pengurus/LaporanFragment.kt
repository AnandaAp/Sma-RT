package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLaporanBinding
import com.maluku.sma_rt.databinding.FragmentLaporanMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.LaporanMasukAdapter
import com.maluku.sma_rt.view.pengurus.adapter.PagerAdapter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface

class LaporanFragment : Fragment(){
    private lateinit var laporanAdapter : PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_laporan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listFragment: ArrayList<Fragment> = arrayListOf(LaporanMasukFragment(),LaporanDiterimaFragment())
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutLaporan)
        val viewPager = view.findViewById<ViewPager2>(R.id.vwLaporanPager)
        laporanAdapter = PagerAdapter(listFragment,this)
        viewPager.adapter = laporanAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Aduan Masuk"
                }
                1->{
                    tab.text = "Aduan Diterima"
                }
            }
        }.attach()
        val backBtn = view.findViewById<TextView>(R.id.btn_back)
        backBtn.setOnClickListener {
            findNavController()!!.popBackStack()
        }
    }
}