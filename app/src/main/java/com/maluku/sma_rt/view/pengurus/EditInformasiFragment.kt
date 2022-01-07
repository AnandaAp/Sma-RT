package com.maluku.sma_rt.view.pengurus

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailInformasiMasukBinding
import com.maluku.sma_rt.databinding.FragmentEditInformasiBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.AdminRTProfilePresenter
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditInformasiFragment : Fragment(), InformasiInterface {
    private lateinit var binding: FragmentEditInformasiBinding
    private var id = ""
    private var judul = ""
    private var kategori = ""
    private var lokasi = ""
    private var detail = ""
    private var gambar = "default_image.jpg"
    private var imageUri: Uri? = null

    val args: EditInformasiFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        judulFocusListener()
        lokasiFocusListener()
        detailFocusListener()
        bindData()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageUri = it
                binding.imageView12.setImageURI(it)
            }
        )
        pickImage(getImage)
        saveInformasi()
    }

    private fun bindData() {
        id = args.idInformasi
        judul = args.judulInformasi
        kategori = args.kategoriInformasi
        lokasi = args.lokasi
        detail = args.detailInformasi
        gambar = args.gambarInformasi
        setEditDataInformasi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentEditInformasiBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setEditDataInformasi() {
        binding.etDetailInformasi.setText(detail)
        binding.etJudul.setText(judul)
        binding.etLokasi.setText(lokasi)
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            if (activity != null){
                // Tampilkan gambar dengan Glide
                Glide.with(this)
                    .load(localFile.path)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                    .into(binding.imageView12)
            }
        }.addOnFailureListener {

        }
        setKategori()
    }

    private fun setKategori(){
        val spKategori = binding.spKategori
        val arrKategori =  resources.getStringArray(R.array.kategori)
        val adapterKategori = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, arrKategori)
        spKategori.adapter = adapterKategori
        for(i in arrKategori.indices){
            if (arrKategori[i]?.lowercase() == kategori.lowercase()){
                spKategori.setSelection(i)
            } else {
                spKategori.setSelection(0)
            }
        }
        spKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                kategori = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun judulFocusListener() {
        binding.etJudul.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etJudul.error = validJudul()
            }
        }
    }

    private fun validJudul(): String? {
        judul = binding.etJudul.text.toString().trim()
        if (judul.isEmpty()){
            return "Masukan judul!"
        }
        return null
    }

    private fun lokasiFocusListener() {
        binding.etLokasi.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etLokasi.error = validLokasi()
            }
        }
    }

    private fun validLokasi(): String? {
        lokasi = binding.etLokasi.text.toString().trim()
        if (lokasi.isEmpty()){
            return "Masukan lokasi!"
        }
        return null
    }

    private fun detailFocusListener() {
        binding.etDetailInformasi.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etDetailInformasi.error = validDetail()
            }
        }
    }

    private fun validDetail(): String? {
        lokasi = binding.etDetailInformasi.text.toString().trim()
        if (lokasi.isEmpty()){
            return "Masukan detail!"
        }
        return null
    }

    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.btnPickImg.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    private fun saveInformasi() {
        binding.button6.setOnClickListener {
            binding.etJudul.clearFocus()
            binding.etLokasi.clearFocus()
            binding.etDetailInformasi.clearFocus()
            validasiUpdateInformasi()
        }
    }

    private fun validasiUpdateInformasi(){
        val validJudul = !binding.etJudul.text.isNullOrEmpty()
        val validLokasi = !binding.etLokasi.text.isNullOrEmpty()
        val validDetail = !binding.etDetailInformasi.text.isNullOrEmpty()

        if (validJudul && validLokasi && validDetail){
            if (imageUri != null){
                uploadImage()
            }
//            Log.d("EDIT_PROFILE","ID = $id, nama = $nama, email = $email, NoHP = $noHp, Gambar = $gambarPengurus, Gender = $jenisKelamin")
            updateInformasi()
        } else {
            if (!validJudul){
                binding.etJudul.error = "Masukkan judul!"
            }
            if (!validLokasi){
                binding.etLokasi.error = "Masukkan lokasi!"
            }
            if (!validDetail){
                binding.etDetailInformasi.error = "Masukkan detail!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateInformasi() {
        InformasiPresenter(this).updateInformasi(getToken(),id,judul,kategori,lokasi,detail,gambar)
    }

    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = "${judul}_${formatter.format(now)}"
        gambar = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                binding.imageView12.setImageURI(null)
//                Toast.makeText(requireContext(),"Upload gambar sukses!",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!", Toast.LENGTH_LONG).show()
            }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    override fun onCreateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetAllInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiSuccess(result: GetInformasiById?) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiSuccess(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_SHORT).show()
        navigateEditToDetailInformasiMasuk()
    }

    override fun onUpdateInformasiFailure(message: String) {
        if (context!=null){
            Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeleteInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    private fun navigateEditToDetailInformasiMasuk(){
        val direction = EditInformasiFragmentDirections.actionEditInformasiFragmentToDetailInformasiMasukFragment2(
            id
        )
        view!!.findNavController()!!.navigate(direction)
    }

}