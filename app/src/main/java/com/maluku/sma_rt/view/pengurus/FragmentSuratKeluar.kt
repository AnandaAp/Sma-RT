package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentSuratKeluarBinding
import com.maluku.sma_rt.view.pengurus.adapter.SuratKeluarAdapter


class FragmentSuratKeluar : Fragment() {
    private lateinit var binding: FragmentSuratKeluarBinding
    private lateinit var rvSuratKeluar: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewSuratKeluar()
    }

    private fun setRecyclerViewSuratKeluar() {
        val suratKeluarAdapter = SuratKeluarAdapter()
        rvSuratKeluar = binding.rvSuratKeluar
        rvSuratKeluar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        rvSuratKeluar.adapter = suratKeluarAdapter
    }

    private fun bindingView(): View {
        binding = FragmentSuratKeluarBinding.inflate(layoutInflater)
        return binding.root
    }
}