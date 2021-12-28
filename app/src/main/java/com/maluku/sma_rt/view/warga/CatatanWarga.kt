package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentCatatanWargaBinding
import com.maluku.sma_rt.databinding.FragmentProdukPageBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTagihanWarga

class CatatanWarga : Fragment() {


    private lateinit var binding: FragmentCatatanWargaBinding
    private lateinit var rvTagihan: RecyclerView
    private lateinit var adapterTagihan: RecyclerViewTagihanWarga


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewListTagihan()
    }


    private fun setRecyclerViewListTagihan() {
        rvTagihan = binding.rvTagihanwarga
        rvTagihan.setHasFixedSize(true)
        rvTagihan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterTagihan = RecyclerViewTagihanWarga()
        rvTagihan.adapter = adapterTagihan
    }




    private fun bindingView(): View {
        binding = FragmentCatatanWargaBinding.inflate(layoutInflater)
        return binding.root
    }

}