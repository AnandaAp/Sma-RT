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
import com.maluku.sma_rt.databinding.FragmentInformasiWargaBinding
import com.maluku.sma_rt.presenter.WargaTagihanPresenter
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInformasiWarga
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTagihanWarga


class InformasiWarga : Fragment() {

    private lateinit var binding: FragmentInformasiWargaBinding
    private lateinit var rvInformasi: RecyclerView
    private lateinit var adapterInformasi: RecyclerViewInformasiWarga

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        setRecyclerViewInformasiWarga()

    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_informasiWarga_to_homeWarga)
        }
    }

    private fun setRecyclerViewInformasiWarga() {
        rvInformasi = binding.rvInformasiWarga
        rvInformasi.setHasFixedSize(true)
        rvInformasi.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterInformasi = RecyclerViewInformasiWarga()
        rvInformasi.adapter = adapterInformasi
    }


    private fun bindingView(): View {
        binding = FragmentInformasiWargaBinding.inflate(layoutInflater)
        return binding.root
    }

}