package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailInformasiBeritaBinding
import com.maluku.sma_rt.databinding.FragmentDetailLaporanListBinding
import com.maluku.sma_rt.databinding.FragmentDetailPesananUserBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.view.activity.MainActivity


class DetailInformasiBerita : Fragment() {

    private lateinit var binding: FragmentDetailInformasiBeritaBinding


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

    private fun goBack(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailInformasiBerita_to_informasiWarga)
        }
    }





    private fun bindingView(): View {
        binding = FragmentDetailInformasiBeritaBinding.inflate(layoutInflater)
        return binding.root
    }

}