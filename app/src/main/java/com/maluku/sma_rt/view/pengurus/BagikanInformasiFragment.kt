package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.net.Uri
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentBagikanInformasiBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "BagiInformasiFragment"
class BagikanInformasiFragment : Fragment(), InformasiInterface {
    private lateinit var binding: FragmentBagikanInformasiBinding
    private var judul: String = ""
    private var lokasi: String = ""
    private var detail: String = ""
    private var kategori: String = ""
    private var imageUri: Uri? = null
    private var gambarInformasi: String = "default_image.jpg"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        judulFocusListener()
        lokasiFocusListener()
        detailFocusListener()
        kategoriFocusListener()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.imageView12.setImageURI(it)
                imageUri = it
            }
        )
        pickImage(getImage)
        buatInformasi()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentBagikanInformasiBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun buatInformasi(){
        binding.btnBuatInformasi.setOnClickListener {
            binding.etJudul.clearFocus()
            binding.etDetailInformasi.clearFocus()
            binding.etLokasi.clearFocus()
            submitForm()
        }
    }

    private fun submitForm() {
        val validJudul = !binding.etJudul.text.isNullOrEmpty()
        val validDetail = !binding.etDetailInformasi.text.isNullOrEmpty()
        val validLokasi = !binding.etLokasi.text.isNullOrEmpty()
        if (validJudul && validDetail && validLokasi){
            if (imageUri != null){
                uploadImage()
            }
            InformasiPresenter(this).createInformasi(getToken(),judul,kategori,lokasi,detail,gambarInformasi)
        } else {
            if (!validJudul){
                binding.etJudul.error = "Masukan judul informasi!"
            }
            if (!validLokasi){
                binding.etLokasi.error = "Masukan lokasi informasi!"
            }
            if (!validDetail){
                binding.etDetailInformasi.error = "Berikan detail informasi!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadImage() {
        val formatter = SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = "${judul}_${formatter.format(now)}"
        gambarInformasi = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Upload gambar sukses!",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!",Toast.LENGTH_LONG).show()
            }
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
            return "Masukan lokasi informasi!"
        }
        return null
    }

    private fun judulFocusListener() {
        binding.etJudul.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etJudul.error = validJudul()
            }
        }
    }

    private fun validJudul(): String?{
        judul = binding.etJudul.text.toString().trim()
        if (judul.isEmpty()){
            return "Masukan judul informasi!"
        }
        return null
    }

    private fun kategoriFocusListener() {
        val spKategori = binding.spKategori
        val arrKategori =  resources.getStringArray(R.array.kategori)
        val adapterKategori = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, arrKategori)
        spKategori.adapter = adapterKategori
        spKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                kategori = adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun detailFocusListener() {
        binding.etDetailInformasi.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etDetailInformasi.error = validDetail()
            }
        }
    }

    private fun validDetail(): String?{
        detail = binding.etDetailInformasi.text.toString()
        if (detail.isEmpty()){
            return "Berikan detail informasi!"
        }
        return null
    }

    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.btnPickImg.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    override fun onCreateInformasiSuccess(message: String) {
        binding.etJudul.text = null
        binding.etLokasi.text = null
        binding.etDetailInformasi.text = null
        binding.imageView12.setImageURI(null)
        if (context!=null){
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateInformasiFailure(message: String) {
        if (context!=null){
            Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
        }
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

    override fun onGetInfoTerkiniSuccess(data: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanSuccess(data: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        return token
    }
}