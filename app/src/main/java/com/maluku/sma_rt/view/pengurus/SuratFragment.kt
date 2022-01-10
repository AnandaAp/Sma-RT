package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.pengurus.adapter.PagerAdapter
import kotlinx.coroutines.currentCoroutineContext

class SuratFragment : Fragment() {
    private lateinit var suratAdapter : PagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_surat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listFragment: ArrayList<Fragment> = arrayListOf(FragmentSuratMasuk(),FragmentSuratKeluar())
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutSurat)
        val viewPager = view.findViewById<ViewPager2>(R.id.vwSuratPager)
        suratAdapter = PagerAdapter(listFragment,this)
        viewPager.adapter = suratAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Surat Masuk"
                }
                1->{
                    tab.text = "Surat Keluar"
                }
            }
        }.attach()
        val backBtn = view.findViewById<TextView>(R.id.btnBackSurat)
        backBtn.setOnClickListener {
            findNavController()!!.popBackStack()
        }
    }
}