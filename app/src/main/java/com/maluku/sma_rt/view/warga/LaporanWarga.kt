package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLaporanWargaBinding
import com.maluku.sma_rt.databinding.FragmentLupaPasswordBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "LAPORAN WARGA"

class LaporanWarga : Fragment(), WargaAduanInterface {

    private lateinit var binding: FragmentLaporanWargaBinding

    private var judul: String = ""
    private var gambar: String = "default_image"
    private var imageUri: Uri? = null
    private var deskripsi: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        judulFocusListener()
        keteranganFocusListener()
        btnBack()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.ivLaporan.setImageURI(it)
                imageUri = it
            }
        )
        pickImage(getImage)
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
            binding.edJudullaporan.clearFocus()
            binding.edKetlaporan.clearFocus()
            validasiKirimLaporan()
        }
    }

    private fun validasiKirimLaporan(){
        val validJudul = !binding.edJudullaporan.text.isNullOrEmpty()
        val validDeskripsi = !binding.edKetlaporan.text.isNullOrEmpty()

        if (validJudul && validDeskripsi){
            if (imageUri != null){
                uploadImage()
            }
            createLaporan()
        } else {
            if (!validJudul){
                binding.edJudullaporan.error = "Masukan judul laporan!"
            }
            if (!validDeskripsi){
                binding.edKetlaporan.error = "Berikan keterangan laporan!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.btnUpload.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = "${judul}_${formatter.format(now)}"
        gambar = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("aduan/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
//                Toast.makeText(requireContext(),"Upload gambar sukses!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!", Toast.LENGTH_LONG).show()
            }
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_laporan)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

        btnSimpan.setOnClickListener {
            dialog.dismiss()
            val direction = LaporanWargaDirections.actionLaporanWargaToHomeWarga()
            findNavController().navigate(direction)
        }

        dialog.show()
    }

    private fun judulFocusListener() {
        binding.edJudullaporan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edJudullaporan.error = judulLaporan()
            }
        }
    }

    private fun judulLaporan(): String? {
        judul = binding.edJudullaporan.text.toString().trim()
        if (judul.isEmpty()){
            return "Masukan judul laporan!"
        }
        return null
    }

    private fun keteranganFocusListener() {
        binding.edKetlaporan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edKetlaporan.error = keteranganLaporan()
            }
        }
    }

    private fun keteranganLaporan(): String? {
        deskripsi = binding.edKetlaporan.text.toString().trim()
        if (deskripsi.isEmpty()){
            return "Masukan keterangan laporan!"
        }
        return null
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun createLaporan() {
        WargaAduanPresenter(this)
            .createAduan(
                getToken(),
                judul,
                gambar,
                deskripsi
            )
    }

    override fun onCreateSuccess(message: String) {
        dialogSukses()
    }

    override fun onCreateFailed(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
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

    override fun onGetAllDataSuccess(list: List<GetAllAduanItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDSuccess(list: GetAduanById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDFailed(message: String) {
        TODO("Not yet implemented")
    }


}