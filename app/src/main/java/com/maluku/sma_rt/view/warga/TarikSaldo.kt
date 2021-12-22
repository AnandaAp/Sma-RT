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
import com.maluku.sma_rt.databinding.FragmentTarikSaldoBinding
import com.maluku.sma_rt.databinding.FragmentTopupSaldoBinding
import com.maluku.sma_rt.extentions.UserSession


class TarikSaldo : Fragment() {

    private lateinit var binding: FragmentTarikSaldoBinding


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
        tarikSaldo()

    }

    private fun bindingView(): View {
        binding = FragmentTarikSaldoBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_tarikSaldo_to_isisaldoTariksaldo)
        }
    }

    private fun tarikSaldo(){
        binding.btnTariksaldo.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_tariksaldo)
            val btnTarik = dialog.findViewById<TextView>(R.id.btn_ok)

            btnTarik.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()
        }
    }
}