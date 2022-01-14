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
import com.maluku.sma_rt.databinding.FragmentTambahKeluargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.presenter.TambahKeluargaPresenter
import com.maluku.sma_rt.view.viewInterface.TambahKeluargaInterface

private const val TAG = "TAMBAH KELUARGA"

class TambahKeluargaFragment : Fragment(), TambahKeluargaInterface {
    private lateinit var binding: FragmentTambahKeluargaBinding
    private lateinit var namaKeluarga: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tambahKeluarga()
        navigateBack()
    }

    private fun tambahKeluarga() {
        binding.btnTambahKeluarga.setOnClickListener {
            binding.inputNamaKeluarga.clearFocus()
            submitForm()
        }
    }

    private fun submitForm(){
        val validNama = !binding.inputNamaKeluarga.text.isNullOrEmpty()
        if (validNama){
            namaKeluarga = binding.inputNamaKeluarga.text.toString()
            addFamily(namaKeluarga)
            dialogTambahKeluargaSukses()
        } else {
            if (!validNama){
                binding.TILinputNamaKeluarga.helperText = "Masukan nama keluarga!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun bindingView(): View {
        binding = FragmentTambahKeluargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun addFamily(nama: String) {
        TambahKeluargaPresenter(requireActivity(),this).addFamilyPresenter(
            getToken(),
            namaKeluarga
        )
    }

    private fun dialogTambahKeluargaSukses(){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_buat_keluarga)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)
        btnSimpan.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onCreateSuccess(message: String) {
        if (context!=null){
            binding.inputNamaKeluarga.text = null
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateFailed(message: String) {
        if (context!=null){
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun navigateBack() {
        binding.btnBack.setOnClickListener{
            findNavController()!!.navigate(R.id.action_tambahKeluargaFragment_to_navigation_home)
        }
    }
}