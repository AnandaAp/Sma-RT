package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.doOnAttach
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.pengurus.adapter.PagerAdapter

class InformasiFragment : Fragment() {
    private var index = 0

    val args: InformasiFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_informasi, container, false)
    }

    private fun bindData() {
        index = args.idNav
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listFragment: ArrayList<Fragment> = arrayListOf(BagikanInformasiFragment(),InformasiMasukFragment())
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayoutInformasi)
        val viewPager = view.findViewById<ViewPager2>(R.id.vwInformasiPager)
        var informasiAdapter = PagerAdapter(listFragment,this)
        viewPager.adapter = informasiAdapter
        TabLayoutMediator(tabLayout,viewPager){tab,position->
            when(position){
                0->{
                    tab.text = "Bagikan Informasi"
                }
                1->{
                    tab.text = "Informasi Masuk"
                }
            }
        }.attach()
        bindData()
        viewPager.post {
            viewPager.setCurrentItem(index, true)
        }
        back(view)
    }

    private fun back(view: View){
        val backBtn = view.findViewById<TextView>(R.id.btnBackInformasi)
        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}