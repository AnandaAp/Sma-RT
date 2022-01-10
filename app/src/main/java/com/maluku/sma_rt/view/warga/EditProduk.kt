package com.maluku.sma_rt.view.warga

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentEditProdukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.produk.GetProdukById
import com.maluku.sma_rt.presenter.ProdukPresenter
import com.maluku.sma_rt.view.viewInterface.ProdukInterface
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "EDIT PRODUK"

class EditProduk : Fragment(), ProdukInterface {
    val args: EditProdukArgs by navArgs()

    private lateinit var binding: FragmentEditProdukBinding

    private var idProduk: String = ""
    private var namaProduk: String = ""
    private var detailProduk: String = ""
    private var hargaProduk: String = ""
    private var gambarProduk: String = "default_image"
    private var statusProduk: Boolean = false
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
        bindData()
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
        updateProduk()
        goBack()
    }

    private fun bindData() {
        idProduk = args.productId
        namaProduk = args.productName
        gambarProduk = args.productImage
        detailProduk = args.productDetail
        hargaProduk = args.productPrice
        statusProduk = args.productStatus.toBoolean()

        binding.edNamaproduk.setText(namaProduk)
        binding.edHargaproduk.setText(hargaProduk)
        binding.edDetailproduk.setText(detailProduk)
        binding.swAktifkanproduk.isChecked = statusProduk

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("produk/${gambarProduk}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            holder.gambarProduk.setImageBitmap(bitmap)
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.imgProduk)
        }.addOnFailureListener {

        }

        konfirmasiHapusProduk()

    }

    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.imgProduk.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    private fun konfirmasiHapusProduk() {
        binding.btnHapusproduk.setOnClickListener {
            var builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Hapus produk ini?")
            builder.setMessage("Kamu yakin untuk menghapus produk ini?")
            builder.setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, id ->
                hapusProduk()
                dialog.cancel()
            })
            builder.setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, id ->
                //
                dialog.cancel()
            })
            var alert: AlertDialog = builder.create()
            alert.show()
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

    private fun updateProduk() {
        binding.btnSimpan.setOnClickListener {
            binding.edNamaproduk.clearFocus()
            binding.edHargaproduk.clearFocus()
            binding.edDetailproduk.clearFocus()
            statusProduk = binding.swAktifkanproduk.isChecked
            validasiUpdateProduk()
        }
    }

    private fun validasiUpdateProduk(){
        val validNama = !binding.edNamaproduk.text.isNullOrEmpty()
        val validHarga = !binding.edHargaproduk.text.isNullOrEmpty()
        val validDetail = !binding.edDetailproduk.text.isNullOrEmpty()

        if (validNama && validHarga && validDetail){
            if (imageUri != null){
                uploadImage()
            }
            updateProduk(namaProduk,detailProduk,gambarProduk,hargaProduk)
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
            Toast.makeText(requireContext(),"Seluruh field harus terisi!",Toast.LENGTH_LONG).show()
        }
    }

    private fun hapusProduk() {
        ProdukPresenter(this).hapusProduk(
            getToken(),
            idProduk
        )
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
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_editProduk_to_produkPage)
        }
    }

    private fun bindingView(): View {
        binding = FragmentEditProdukBinding.inflate(layoutInflater)
        return binding.root
    }

    fun updateProduk(nama: String, detail: String, gambar: String, harga: String) {
        ProdukPresenter(this).updateProduk(
            getToken(),
            idProduk,
            namaProduk,
            detailProduk,
            gambarProduk,
            hargaProduk,
            statusProduk.toString()
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
        TODO("Not yet implemented")
    }

    override fun onCreateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            val direction = EditProdukDirections.actionEditProdukToProdukPage()
            findNavController().navigate(direction)
        }
    }

    override fun onUpdateFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDeleteSuccess(message: String) {
        Handler(Looper.getMainLooper()).post(Runnable { //do stuff like remove view etc
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            val direction = EditProdukDirections.actionEditProdukToProdukPage()
            findNavController().navigate(direction)
        })
    }

    override fun onDeleteFailure(message: String) {
        Handler(Looper.getMainLooper()).post(Runnable { //do stuff like remove view etc
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        })
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }
}