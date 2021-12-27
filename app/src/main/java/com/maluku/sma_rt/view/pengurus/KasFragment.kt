package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentKasBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetrt.GetAllDompetRTItem
import com.maluku.sma_rt.model.dompetrt.GetDompetById
import com.maluku.sma_rt.presenter.AdminRTKasPresenter
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import java.text.NumberFormat
import java.util.*

class KasFragment : Fragment(), DompetRTInterface {
    private lateinit var binding: FragmentKasBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    private fun bindingView(): View {
        binding = FragmentKasBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateKasToRiwayat()
        navigateKasToBuatTagihan()
        AdminRTKasPresenter(this).getDompetRT(getToken())
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    override fun onGetAllDataSuccess(result: GetDompetById?) {
        binding.tvTotalKas.text = rupiah(result?.jumlah.toString().toDouble())
    }

    override fun onGetAllDataFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    private fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    private fun navigateKasToRiwayat() {
        binding.btnRiwayatKas.setOnClickListener{
            findNavController().navigate(R.id.action_kasFragment_to_riwayatKasFragment)
        }
    }

    private fun navigateKasToBuatTagihan() {
        binding.btnBuatTagihan.setOnClickListener{
            findNavController().navigate(R.id.action_kasFragment_to_buatIuranFragment22)
        }
    }


}