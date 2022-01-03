package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentTopupSaldoBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetkeluarga.GetAllDompetKeluargaItem
import com.maluku.sma_rt.model.dompetkeluarga.GetDompetKeluargaById
import com.maluku.sma_rt.presenter.DompetKeluargaPresenter
import com.maluku.sma_rt.view.viewInterface.DompetKeluargaInterface
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TOP UP SALDO"

class TopupSaldo : Fragment(), DompetKeluargaInterface {

    private lateinit var binding: FragmentTopupSaldoBinding

    private var jumlahTopUp: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        jumlahTopUpFocusListener()
        submitTopUpSaldo()
    }

    private fun submitTopUpSaldo(){
        binding.btnIsisaldo.setOnClickListener {
            binding.edIsisaldo.clearFocus()
            validasiTopUpSaldo()
        }
    }

    private fun validasiTopUpSaldo(){
        val validJumlahTopUp = !binding.edIsisaldo.text.isNullOrEmpty()

        if (validJumlahTopUp){
            topUpSaldo()
        } else {
            if (!validJumlahTopUp){
                binding.edIsisaldo.error = "Masukan jumlah top up!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun jumlahTopUpFocusListener() {
        binding.edIsisaldo.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edIsisaldo.error = jumlahTopUp()
            }
        }
    }

    private fun jumlahTopUp(): String? {
        jumlahTopUp = binding.edIsisaldo.text.toString().trim()
        if (jumlahTopUp.isEmpty()){
            return "Masukan jumlah top up!"
        }
        return null
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_topupsaldo)
        val btnTopup = dialog.findViewById<TextView>(R.id.btn_ok)
        val message = dialog.findViewById<TextView>(R.id.tv_tambahsaldo)

        message.text = "Saldo sebesar ${toRupiah(jumlahTopUp.toDouble())} berhasil ditambahkan"

        btnTopup.setOnClickListener {
            dialog.dismiss()
            val direction = TopupSaldoDirections.actionTopupSaldoToHomeWarga()
            findNavController().navigate(direction)
        }

        dialog.show()
    }


    private fun bindingView(): View {
        binding = FragmentTopupSaldoBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_topupSaldo_to_homeWarga)
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun topUpSaldo(){
        DompetKeluargaPresenter(this).topup(
            getToken(),
            jumlahTopUp
        )
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    override fun onTopupSuccess(message: String) {
        dialogSukses()
    }

    override fun onTopupFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onWithdrawSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onWithdrawFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataSuccess(list: List<GetAllDompetKeluargaItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(list: GetDompetKeluargaById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
    }
}
