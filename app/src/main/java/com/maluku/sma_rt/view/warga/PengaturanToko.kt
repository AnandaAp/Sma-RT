package com.maluku.sma_rt.view.warga

import android.app.Activity
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPengaturanTokoBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya
import com.maluku.sma_rt.presenter.WargaKelolaTokoPresenter
import com.maluku.sma_rt.view.viewInterface.KelolaTokoInterface
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "PENGATURAN TOKO"


class PengaturanToko : Fragment(), KelolaTokoInterface {
    val args: PengaturanTokoArgs by navArgs()


    private lateinit var binding: FragmentPengaturanTokoBinding

    private var idKeluarga: String = ""
    private var namaToko: String = ""
    private var gambar: String = "default_image"
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        goBack()
        namaTokoFocusListener()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                binding.profileToko.setImageURI(it)
                imageUri = it
            }
        )
        pickImage(getImage)
        submitUpdateToko()
    }

    private fun bindData() {
        idKeluarga = args.keluargaId
        namaToko = args.keluargaNamaToko
        gambar = args.keluargaGambar

        binding.edNamatoko.setText(namaToko)

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("toko/${gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            holder.gambarProduk.setImageBitmap(bitmap)
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.profileToko)
        }.addOnFailureListener {

        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_pengaturanToko_to_kelolaToko)
        }
    }


    private fun bindingView(): View {
        binding = FragmentPengaturanTokoBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun submitUpdateToko(){
        binding.btnUbahtoko.setOnClickListener {
            binding.edNamatoko.clearFocus()
            validasiUpdateToko()
        }
    }

    private fun validasiUpdateToko(){
        val validNamaToko = !binding.edNamatoko.text.isNullOrEmpty()

        if (validNamaToko){
            if (imageUri != null){
                uploadImage()
            }
            updateToko()
        } else {
            if (!validNamaToko){
                binding.edNamatoko.error = "Masukan nama toko!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }


    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.btnPickprofile.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = "${namaToko}_${formatter.format(now)}"
        gambar = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("toko/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
//                Toast.makeText(requireContext(),"Upload gambar sukses!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!", Toast.LENGTH_LONG).show()
            }
    }

    private fun namaTokoFocusListener() {
        binding.edNamatoko.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNamatoko.error = namaToko()
            }
        }
    }

    private fun namaToko(): String? {
        namaToko = binding.edNamatoko.text.toString().trim()
        if (namaToko.isEmpty()){
            return "Masukan nama toko!"
        }
        return null
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_profiletoko)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

        btnSimpan.setOnClickListener {
            dialog.dismiss()
            val direction = PengaturanTokoDirections.actionPengaturanTokoToKelolaToko()
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

    private fun updateToko() {
        WargaKelolaTokoPresenter(requireActivity(),this).updateToko(
            getToken(),
            idKeluarga,
            namaToko,
            gambar
        )
    }

    override fun onUpdateSuccess(message: String) {
        dialogSukses()
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetDataSuccess(list: GetKeluargaSaya?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailed(message: String) {
        TODO("Not yet implemented")
    }

}