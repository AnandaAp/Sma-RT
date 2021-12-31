package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.pengurus.adapter.PagerAdapter

class ListKasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_kas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listFragment: ArrayList<Fragment> = arrayListOf(DaftarBuatIuranFragment(),RiwayatKasFragment())
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutKas)
        val viewPager = view.findViewById<ViewPager2>(R.id.vwKasPager)
        val kasAdapter = PagerAdapter(listFragment,this)
        viewPager.adapter = kasAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Belum Bayar"
                }
                1->{
                    tab.text = "Sudah Bayar"
                }
            }
        }.attach()
    }
}