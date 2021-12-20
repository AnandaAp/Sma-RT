package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentSuratMasukBinding
import com.maluku.sma_rt.view.pengurus.adapter.SuratMasukAdapter


class FragmentSuratMasuk : Fragment() {
    private lateinit var binding: FragmentSuratMasukBinding
    private lateinit var rvSuratMasuk: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewSuratMasuk()
    }

    private fun setRecyclerViewSuratMasuk() {
        val suratMasukAdapter = SuratMasukAdapter()
        rvSuratMasuk = binding.rvSuratMasuk
        rvSuratMasuk.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        rvSuratMasuk.adapter = suratMasukAdapter
    }

    private fun bindingView(): View {
        binding = FragmentSuratMasukBinding.inflate(layoutInflater)
        return binding.root
    }
}