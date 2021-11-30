package com.maluku.sma_rt.view.warga.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.maluku.sma_rt.view.warga.PesananDiproses
import com.maluku.sma_rt.view.warga.PesananMasuk
import com.maluku.sma_rt.view.warga.PesananSelesai

class ViewPagerPesanan(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position) {
            0 -> fragment = PesananMasuk()
            1 -> fragment = PesananDiproses()
            2 -> fragment = PesananSelesai()

        }
        return fragment
    }
}