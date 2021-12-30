package com.maluku.sma_rt.view.warga

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentTambahProdukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.produk.GetProdukById
import com.maluku.sma_rt.presenter.ProdukPresenter
import com.maluku.sma_rt.view.viewInterface.ProdukInterface
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "TAMBAH PRODUK"

class TambahProduk : Fragment(), ProdukInterface {

    private lateinit var binding: FragmentTambahProdukBinding

    private var namaProduk: String = ""
    private var detailProduk: String = "aaa"
    private var hargaProduk: String = ""
    private var gambarProduk: String = "default_image"
    private var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        namaFocusListener()
        hargaFocusListener()
        detailFocusListener()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageUri = it
                binding.imgProduk.setImageURI(it)
            }
        )
        pickImage(getImage)
        tambahProduk()
        goBack()
    }

    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.pickerGallery.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = "${namaProduk}_${formatter.format(now)}"
        gambarProduk = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("produk/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                binding.imgProduk.setImageURI(null)
//                Toast.makeText(requireContext(),"Upload gambar sukses!",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!",Toast.LENGTH_LONG).show()
            }
    }

    private fun tambahProduk() {
        binding.btnSimpan.setOnClickListener {
            binding.edNamaproduk.clearFocus()
            binding.edHargaproduk.clearFocus()
            binding.edDetailproduk.clearFocus()
            validasiTambahProduk()
        }
    }

    private fun validasiTambahProduk(){
        val validNama = !binding.edNamaproduk.text.isNullOrEmpty()
        val validHarga = !binding.edHargaproduk.text.isNullOrEmpty()
        val validDetail = !binding.edDetailproduk.text.isNullOrEmpty()

        if (validNama && validHarga && validDetail){
            if (imageUri != null){
                uploadImage()
            }
            tambahProduk(namaProduk,detailProduk,gambarProduk,hargaProduk)
        } else {
            if (!validNama){
                binding.edNamaproduk.error = "Masukan nama produk!"
            }
            if (!validDetail){
                binding.edDetailproduk.error = "Berikan deskripsi produk!"
            }
            if (!validHarga){
                binding.edHargaproduk.error = "Masukan harga!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun namaFocusListener() {
        binding.edNamaproduk.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNamaproduk.error = validNamaProduk()
            }
        }
    }

    private fun validNamaProduk(): String? {
        namaProduk = binding.edNamaproduk.text.toString().trim()
        if (namaProduk.isEmpty()){
            return "Masukan nama produk!"
        }
        return null
    }

    private fun hargaFocusListener() {
        binding.edHargaproduk.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edHargaproduk.error = validHargaProduk()
            }
        }
    }

    private fun validHargaProduk(): String? {
        hargaProduk = binding.edHargaproduk.text.toString().trim()
        if (hargaProduk.isEmpty()){
            return "Masukan harga produk!"
        }
        return null
    }

    private fun detailFocusListener() {
        binding.edDetailproduk.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edDetailproduk.error = validDetailProduk()
            }
        }
    }

    private fun validDetailProduk(): String? {
        detailProduk = binding.edDetailproduk.text.toString().trim()
        if (detailProduk.isEmpty()){
            return "Masukan detail produk!"
        }
        return null
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_tambahProduk_to_produkPage)
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindingView(): View {
        binding = FragmentTambahProdukBinding.inflate(layoutInflater)
        return binding.root
    }

    fun tambahProduk(nama: String, detail: String, gambar: String, harga: String) {
        ProdukPresenter(this).tambahProduk(
            getToken(),
            namaProduk,
            detailProduk,
            gambarProduk,
            hargaProduk
        )
    }

    override fun onGetAllDataSuccess(data: List<GetAllProdukKeluargaItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(data: GetProdukById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            val direction = TambahProdukDirections.actionTambahProdukToProdukPage()
            findNavController().navigate(direction)
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
}