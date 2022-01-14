package com.maluku.sma_rt.view.pengurus

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
import com.maluku.sma_rt.databinding.FragmentBuatIuranBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.presenter.AdminTagihanPresenter
import com.maluku.sma_rt.view.viewInterface.AdminTagihanInterface


class BuatIuranFragment : Fragment(), AdminTagihanInterface {
    private lateinit var binding: FragmentBuatIuranBinding
    private var nama: String = ""
    private var jumlah: String = ""
    private var detail: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        namaFocusListener()
        jumlahFocusListener()
        detailFocusListener()
        buatTagihan()
        navigateBackToKas()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentBuatIuranBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun buatTagihan(){
        binding.btnSimpanTagihan.setOnClickListener {
            binding.etNamaTagihan.clearFocus()
            binding.etJumlah.clearFocus()
            binding.etDetail.clearFocus()
            submitForm()
        }
    }


    private fun submitForm() {
        val validNama = !binding.etNamaTagihan.text.isNullOrEmpty()
        val validJumlah = !binding.etJumlah.text.isNullOrEmpty()
        val validDetail = !binding.etDetail.text.isNullOrEmpty()
        if (validNama && validDetail && validJumlah){
            Log.d("CREATE_IURAN","Nama: $nama, Jumlah: $jumlah, Detail: $detail")
            AdminTagihanPresenter(this).createTagihan(nama,detail,jumlah,getToken())
            dialogBuatIuranSukses()
        } else {
            if (!validNama){
                binding.TILnamaTagihan.helperText = "Masukan nama tagihan!"
            }
            if (!validJumlah){
                binding.TILjumlahTagihan.helperText = "Masukan jumlah tagihan!"
            }
            if (!validDetail){
                binding.TILdetailTagihan.helperText = "Berikan detail tagihan!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun dialogBuatIuranSukses(){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_buat_tagihan)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)
        btnSimpan.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun namaFocusListener() {
        binding.etNamaTagihan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILnamaTagihan.helperText = validNama()
            }
        }
    }

    private fun validNama(): String? {
        nama = binding.etNamaTagihan.text.toString().trim()
        if (nama.isEmpty()){
            return "Masukan nama tagihan!"
        }
        return null
    }

    private fun jumlahFocusListener() {
        binding.etJumlah.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILjumlahTagihan.helperText = validJumlah()
            }
        }
    }

    private fun validJumlah(): String? {
        jumlah = binding.etJumlah.text.toString().trim()
        if (jumlah.isEmpty()){
            return "Masukan jumlah tagihan!"
        }
        return null
    }

    private fun detailFocusListener() {
        binding.etDetail.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILdetailTagihan.helperText = validDetail()
            }
        }
    }

    private fun validDetail(): String? {
        detail = binding.etDetail.text.toString()
        if (detail.isEmpty()){
            return "Masukan detail tagihan!"
        }
        return null
    }

    override fun onCreateSuccess(message: String) {
        binding.etNamaTagihan.text = null
        binding.etDetail.text = null
        binding.etJumlah.text = null
        if (context!=null){
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateFailed(message: String) {
        if (context!=null){
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetDataSuccess(message: String, list: List<GetAllTagihanItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    private fun navigateBackToKas(){
        binding.btnBack.setOnClickListener {
            findNavController()!!.navigate(R.id.action_buatIuranFragment2_to_kasFragment)
        }
    }

}