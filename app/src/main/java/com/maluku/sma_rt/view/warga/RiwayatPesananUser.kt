package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRiwayatPesananUserBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewRiwayatPesananUser

class RiwayatPesananUser : Fragment() {

    private lateinit var binding: FragmentRiwayatPesananUserBinding
    private lateinit var rvRiwayatPesanan: RecyclerView
    private lateinit var adapterRiwayatPesanan: RecyclerViewRiwayatPesananUser



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goback()
        setRecyclerViewRiwayatPesanan()


    }

    private fun goback() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatPesananUser_to_jualbeliWarga)
        }
    }

    private fun setRecyclerViewRiwayatPesanan() {
        rvRiwayatPesanan = binding.rvRiwayatpesanan
        rvRiwayatPesanan.setHasFixedSize(true)
        rvRiwayatPesanan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterRiwayatPesanan = RecyclerViewRiwayatPesananUser()
        rvRiwayatPesanan.adapter = adapterRiwayatPesanan
    }


    private fun bindingView(): View {
        binding = FragmentRiwayatPesananUserBinding.inflate(layoutInflater)
        return binding.root
    }

}