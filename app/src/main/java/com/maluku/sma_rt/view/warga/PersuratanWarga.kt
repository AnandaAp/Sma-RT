package com.maluku.sma_rt.view.warga

import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPersuratanWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.model.persuratan.GetPersuratanById
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.presenter.WargaPersuratanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaPersuratanInterface
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

private const val TAG = "PERSURATAN WARGA"

class PersuratanWarga : Fragment(), WargaPersuratanInterface {

    private lateinit var binding: FragmentPersuratanWargaBinding

    private var nama: String = ""
    private var judul: String = ""
    private var keperluan: String = ""
    private var tanggal: String = ""

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
            findNavController().navigate(R.id.action_persuratanWarga_to_persuratanPage)
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

        picker.addOnPositiveButtonClickListener{
            tvTanggal.setText(outputDateFormat.format(it))
            tanggal = outputDateFormat.format(it)
        }

//        picker.addOnPositiveButtonClickListener {
//            tvTanggal.setText(" Pada tanggal : "+picker.headerText)
//        }
    }

    private val outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone("UTC")
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
            binding.edNama.clearFocus()
            binding.edJudulsurat.clearFocus()
            binding.layoutTanggal.clearFocus()
            binding.edKeperluan.clearFocus()
            validasiBuatSurat()
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
            val direction = PersuratanWargaDirections.actionPersuratanWargaToPersuratanPage()
            findNavController().navigate(direction)
        }

        dialog.show()
    }

    private fun validasiBuatSurat(){
        val validJudul = !binding.edJudulsurat.text.isNullOrEmpty()
        val validNama = !binding.edNama.text.isNullOrEmpty()
        val validKeperluan = !binding.edKeperluan.text.isNullOrEmpty()
        val validTanggal = !tanggal.isNullOrEmpty()
        val validKonfirmasi = binding.checkBox.isChecked

        if (validJudul && validNama && validKeperluan && validTanggal && validKonfirmasi){
            createSurat()
        } else {
            if (!validJudul){
                binding.edJudulsurat.error = "Masukan judul surat!"
            }
            if (!validNama){
                binding.edNama.error = "Masukkan nama surat!"
            }
            if (!validTanggal){
                Toast.makeText(requireContext(),"Pilih tanggal surat!", Toast.LENGTH_LONG).show()
            }
            if (!validKeperluan){
                binding.edKeperluan.error = "Masukkan keperluan surat!"
            }
            if (!binding.checkBox.isChecked){
                Toast.makeText(context,"Anda harus mencentang pernyataan!",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun createSurat() {
        WargaPersuratanPresenter(this)
            .createPersuratan(
                getToken(),
                judul,
                nama,
                tanggal,
                keperluan
            )
    }

    override fun onCreateSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            dialogSukses()
        }
    }

    override fun onCreateFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(result: List<GetAllPersuratanItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetDataByIDSuccess(data: GetPersuratanById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDFailure(message: String) {
        TODO("Not yet implemented")
    }


}