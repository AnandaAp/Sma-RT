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
import com.maluku.sma_rt.databinding.FragmentPersuratanPageBinding
import com.maluku.sma_rt.databinding.FragmentPersuratanWargaBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewPersuratanPage
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewSuratMasuk

class PersuratanPage : Fragment() {

    private lateinit var binding: FragmentPersuratanPageBinding

    private lateinit var rvSuratPage: RecyclerView
    private lateinit var adapterSuratPage: RecyclerViewPersuratanPage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack()
        setRecyclerViewPersuratanPage()
        btnSuratMasuk()
        fabAddSurat()

    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanPage_to_homeWarga)
        }
    }

    private fun setRecyclerViewPersuratanPage() {
        rvSuratPage = binding.rvPersuratanpage
        rvSuratPage.setHasFixedSize(true)
        rvSuratPage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterSuratPage = RecyclerViewPersuratanPage()
        rvSuratPage.adapter = adapterSuratPage
    }

    private fun btnSuratMasuk() {
        binding.btnSuratmasuk.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanPage_to_persuratanWargaMasuk)
        }
    }

    private fun fabAddSurat() {
        binding.addSurat.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanPage_to_persuratanWarga)
        }
    }



        private fun bindingView(): View {
            binding = FragmentPersuratanPageBinding.inflate(layoutInflater)
            return binding.root
        }

    }