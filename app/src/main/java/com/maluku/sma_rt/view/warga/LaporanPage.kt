package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLaporanPageBinding
import com.maluku.sma_rt.view.pengurus.BagikanInformasiFragment
import com.maluku.sma_rt.view.pengurus.InformasiMasukFragment
import com.maluku.sma_rt.view.pengurus.adapter.PagerAdapter
import com.maluku.sma_rt.view.warga.adapter.ViewPagerLaporan

class LaporanPage : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laporan_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listFragment: ArrayList<Fragment> = arrayListOf(
            LaporanSaya(),
            LaporanList()
        )
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutLaporan)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerLaporan)
        val AdapterLaporan = ViewPagerLaporan(listFragment,this)
        viewPager.adapter = AdapterLaporan
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Laporan Saya"
                }
                1->{
                    tab.text = "List Laporan"
                }
            }
        }.attach()
        val backBtn = view.findViewById<TextView>(R.id.btnBackLaporanPage)
        backBtn.setOnClickListener {
           findNavController().navigate(R.id.action_laporanPage_to_homeWarga)
        }
    }

}