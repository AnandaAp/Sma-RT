package com.maluku.sma_rt.view.warga

import android.app.Dialog
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
import com.maluku.sma_rt.databinding.FragmentLaporanWargaBinding
import com.maluku.sma_rt.databinding.FragmentLupaPasswordBinding
import com.maluku.sma_rt.extentions.UserSession

class LaporanWarga : Fragment() {

    private lateinit var binding: FragmentLaporanWargaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack()
        kirimLaporan()

    }

    private fun bindingView(): View {
        binding = FragmentLaporanWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_laporanWarga_to_homeWarga)
        }
    }

    private fun kirimLaporan(){
        binding.btnKirim.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_laporan)
            val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

            btnSimpan.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()
        }
    }


}