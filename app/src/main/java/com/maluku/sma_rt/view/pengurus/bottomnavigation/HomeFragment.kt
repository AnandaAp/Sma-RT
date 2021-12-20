package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentHomeBinding
import com.maluku.sma_rt.view.pengurus.adapter.GaleriAdapter
import com.maluku.sma_rt.view.pengurus.adapter.InfoAdapter

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvGaleri: RecyclerView
    private lateinit var adapterGaleri: GaleriAdapter
    private lateinit var rvInfo: RecyclerView
    private lateinit var adapterInfo: InfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewDaftarWarga()
        navigateDashboardToTambahKelurga()
        navigateDashboardToSurat()
    }

    private fun setRecyclerViewDaftarWarga(){
        rvGaleri = binding.rvGaleriWarga
        adapterGaleri = GaleriAdapter()
        rvInfo = binding.rvInfoTerkini
        adapterInfo = InfoAdapter()
        rvGaleri.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvGaleri.adapter = adapterGaleri
        rvInfo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvInfo.adapter = adapterInfo
    }

    private fun navigateDashboardToTambahKelurga() {
        binding.btnTambahKeluarga.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_tambahKeluargaFragment)
        }
    }

    private fun navigateDashboardToSurat() {
        binding.btnSurat.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_suratFragment)
        }
    }

    private fun bindingView(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

}