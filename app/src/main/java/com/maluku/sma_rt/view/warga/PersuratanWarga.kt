package com.maluku.sma_rt.view.warga

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPersuratanWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS


class PersuratanWarga : Fragment() {

    private lateinit var binding: FragmentPersuratanWargaBinding

    private var nama: String = ""
    private var judul: String = ""
    private var keperluan: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack()
        tanggalPersuratan()
        namaFocusListener()
        judulFocusListener()
        keperluanFocusListener()
        ajukanPersuratan()
    }


    private fun bindingView(): View {
        binding = FragmentPersuratanWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanWarga_to_homeWarga)
        }
    }

    private  fun tanggalPersuratan(){
        val builder : MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()
        val picker : MaterialDatePicker<*> = builder.build()

        val tvTanggal = binding.tvTanggal
        val btnTanggal = binding.btnTanggal

        binding.layoutTanggal.setOnClickListener {
            picker.show(childFragmentManager, picker.toString())
        }

        btnTanggal.setOnClickListener {
            picker.show(childFragmentManager, picker.toString())
        }

        picker.addOnPositiveButtonClickListener {
            tvTanggal.setText(" Pada tanggal : "+picker.headerText)
        }
    }

    private fun namaFocusListener() {
        binding.edNama.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNama.error = namaLaporan()
            }
        }
    }

    private fun namaLaporan(): String? {
        nama = binding.edNama.text.toString().trim()
        if (nama.isEmpty()){
            return "Masukan nama surat!"
        }
        return null
    }

    private fun judulFocusListener() {
        binding.edJudulsurat.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edJudulsurat.error = judulSurat()
            }
        }
    }

    private fun judulSurat(): String? {
        judul = binding.edJudulsurat.text.toString().trim()
        if (judul.isEmpty()){
            return "Masukan judul surat!"
        }
        return null
    }

    private fun keperluanFocusListener() {
        binding.edKeperluan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edKeperluan.error = keperluanLaporan()
            }
        }
    }

    private fun keperluanLaporan(): String? {
        keperluan = binding.edKeperluan.text.toString().trim()
        if (keperluan.isEmpty()){
            return "Masukan keperluan surat!"
        }
        return null
    }

    private fun ajukanPersuratan(){
        binding.btnAjukan.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()

        }
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_persuratan)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

        btnSimpan.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}