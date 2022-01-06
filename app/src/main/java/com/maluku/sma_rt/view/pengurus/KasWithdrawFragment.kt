package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentKasWithdrawBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetrt.GetDompetById
import com.maluku.sma_rt.presenter.AdminTagihanPresenter
import com.maluku.sma_rt.presenter.DompetRTPresenter
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import java.text.NumberFormat
import java.util.*

class KasWithdrawFragment : Fragment(), DompetRTInterface {
    private lateinit var binding: FragmentKasWithdrawBinding
    private var jumlah: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    private fun bindingView(): View {
        binding = FragmentKasWithdrawBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DompetRTPresenter(this).getDompetRTByLogin(getToken())
        jumlahFocusListener()
        navigateBackToKas()
        withdraw()
    }

    private fun withdraw(){
        binding.btnWithdrawRT.setOnClickListener {
            binding.etJumlahWithdraw.clearFocus()
            submitForm()
        }
    }

    private fun submitForm() {
        val validJumlah = !binding.etJumlahWithdraw.text.isNullOrEmpty()
        if (validJumlah){
            DompetRTPresenter(this).withdrawAdmin(getToken(),jumlah)
        } else {
            if (!validJumlah){
                binding.TILjmlWithdraw.helperText = "Masukan jumlah withdraw!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun jumlahFocusListener() {
        binding.etJumlahWithdraw.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILjmlWithdraw.helperText = validJumlah()
            }
        }
    }

    private fun validJumlah(): String? {
        jumlah = binding.etJumlahWithdraw.text.toString()
        if (jumlah.isEmpty()){
            return "Masukan jumlah withdraw!"
        }
        return null
    }


    override fun onGetAllDataSuccess(result: GetDompetById?) {
        if (result != null) {
            binding.tvTotalKas.text = rupiah(result.jumlah.toString().toDouble())
        }
    }

    override fun onGetAllDataFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    override fun onWithdrawSuccess(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
        navigateWithdrawToKas()
    }

    override fun onWithdrawFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    private fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    private fun navigateWithdrawToKas() {
        findNavController()!!.navigate(R.id.action_kasWithdrawFragment_to_kasFragment)
    }

    private fun navigateBackToKas(){
        binding.btnBack.setOnClickListener {
            findNavController()!!.popBackStack()
        }
    }
}