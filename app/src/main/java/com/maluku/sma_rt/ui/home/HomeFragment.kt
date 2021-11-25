package com.maluku.sma_rt.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentHomeBinding
import com.maluku.sma_rt.view.pengurus.adapter.GaleriAdapter
import com.maluku.sma_rt.view.pengurus.adapter.InfoAdapter
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewGaleri
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfo

class HomeFragment : Fragment() {
    private lateinit var rvGaleri: RecyclerView
    private lateinit var adapterGaleri: GaleriAdapter
    private lateinit var rvInfo: RecyclerView
    private lateinit var adapterInfo: InfoAdapter
    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        rvGaleri = view.findViewById(R.id.rv_galeriWarga)
        adapterGaleri = GaleriAdapter()
        rvInfo = view.findViewById(R.id.rv_infoTerkini)
        adapterInfo = InfoAdapter()
        rvGaleri.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvGaleri.adapter = adapterGaleri
        rvInfo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvInfo.adapter = adapterInfo

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}