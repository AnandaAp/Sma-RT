package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailPesananUserBinding
import com.maluku.sma_rt.databinding.FragmentIsisaldoTariksaldoBinding


class DetailPesananUser : Fragment() {

    private lateinit var binding: FragmentDetailPesananUserBinding

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

    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailPesananUser_to_riwayatPesananUser)
        }
    }

    private fun bindingView(): View {
        binding = FragmentDetailPesananUserBinding.inflate(layoutInflater)
        return binding.root
    }


}