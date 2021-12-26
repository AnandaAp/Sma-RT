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
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentTarikSaldoBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetkeluarga.GetAllDompetKeluargaItem
import com.maluku.sma_rt.model.dompetkeluarga.GetDompetKeluargaById
import com.maluku.sma_rt.presenter.DompetKeluargaPresenter
import com.maluku.sma_rt.view.viewInterface.DompetKeluargaInterface
import java.text.NumberFormat
import java.util.*

private const val TAG = "TARIK SALDO"

class TarikSaldo : Fragment(), DompetKeluargaInterface {

    private lateinit var binding: FragmentTarikSaldoBinding

    private var jumlahSaldo: String = ""
    private var jumlahWithdraw: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DompetKeluargaPresenter(this).getDompetKeluargaByLoginSession(getToken())
        goBack()
        jumlahWithdrawFocusListener()
        submitWithdraw()
    }

    private fun setDompetKeluarga(list: GetDompetKeluargaById) {
        jumlahSaldo = toRupiah(list.jumlah.toString().toDouble())
        binding.tvJumlahsaldo.text = jumlahSaldo
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

    private fun submitWithdraw(){
        binding.btnTariksaldo.setOnClickListener {
            binding.edIsisaldo.clearFocus()
            validasiWithdraw()
        }
    }

    private fun validasiWithdraw(){
        val validJumlahWithdraw = !binding.edIsisaldo.text.isNullOrEmpty()

        if (validJumlahWithdraw){
            withdrawSaldo()
        } else {
            if (!validJumlahWithdraw){
                binding.edIsisaldo.error = "Masukan jumlah saldo yang akan ditarik!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun jumlahWithdrawFocusListener() {
        binding.edIsisaldo.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edIsisaldo.error = jumlahWithdraw()
            }
        }
    }

    private fun jumlahWithdraw(): String? {
        jumlahWithdraw = binding.edIsisaldo.text.toString().trim()
        if (jumlahWithdraw.isEmpty()){
            return "Masukan jumlah saldo yang akan ditarik!"
        }
        return null
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_tariksaldo)
        val btnTarik = dialog.findViewById<TextView>(R.id.btn_ok)

        btnTarik.setOnClickListener {
            dialog.dismiss()
            val direction = TarikSaldoDirections.actionTarikSaldoToIsisaldoTariksaldo()
            findNavController().navigate(direction)
        }

        dialog.show()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun withdrawSaldo(){
        DompetKeluargaPresenter(this).withdraw(
            getToken(),
            jumlahWithdraw
        )
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    override fun onTopupSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onTopupFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onWithdrawSuccess(message: String) {
        dialogSukses()
    }

    override fun onWithdrawFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetAllDataSuccess(list: List<GetAllDompetKeluargaItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(list: GetDompetKeluargaById?) {
        setDompetKeluarga(list!!)
    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }
}