package com.maluku.sma_rt.view.warga

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentAkunWargaBinding
import com.maluku.sma_rt.view.activity.DashboardWargaActivity




class AkunWarga : Fragment() {

    private lateinit var binding: FragmentAkunWargaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkunWargaBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener{
            val intent = Intent (activity, DashboardWargaActivity::class.java)
            startActivity(intent)
        }

        binding.menuKelolatoko.setOnClickListener{
            findNavController().navigate(R.id.action_akunWarga_to_kelolaToko)
        }





        return binding.root
    }


}