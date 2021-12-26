package com.maluku.sma_rt.view.warga

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
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
import android.widget.ImageView
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
import com.maluku.sma_rt.databinding.FragmentWargaEditProfileBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.WargaEditProfilePresenter
import com.maluku.sma_rt.view.viewInterface.WargaEditProfileInterface
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "EDIT PROFILE"

class EditProfile : Fragment(), WargaEditProfileInterface {
    val args: EditProfileArgs by navArgs()

    private lateinit var binding: FragmentWargaEditProfileBinding

    private var idWarga: String = ""
    private var nama: String = ""
    private var noHp: String = ""
    private var email: String = ""
    private var gender: String = ""
    private var gambarWarga: String = "default_image"
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
        namaFocusListener()
        emailFocusListener()
        noHpFocusListener()
        genderFocusListener()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageUri = it
                binding.profileImage.setImageURI(it)
            }
        )
        pickImage(getImage)
        saveProfile()
        goBack()
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_editProfile_to_akunWarga)
        }
    }


    private fun bindingView(): View {
        binding = FragmentWargaEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }


    private fun bindData() {
        idWarga = args.wargaId
        nama = args.wargaNama
        noHp = args.wargaNohp
        email = args.wargaEmail
        gender = args.wargaGender
        gambarWarga = args.wargaGambar

        binding.edNamawarga.setText(nama)
        binding.edEmailwarga.setText(email)
        binding.edJeniskelaminwarga.setText(gender)
        binding.edNotelpwarga.setText(noHp)

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${gambarWarga}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            holder.gambarProduk.setImageBitmap(bitmap)
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.profileImage)
        }.addOnFailureListener {

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
        val fileName = "${nama}_${formatter.format(now)}"
        gambarWarga = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("user/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                binding.profileImage.setImageURI(null)
//                Toast.makeText(requireContext(),"Upload gambar sukses!",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!", Toast.LENGTH_LONG).show()
            }
    }

    private fun saveProfile() {
        binding.btnSimpanprofile.setOnClickListener {
            binding.edNamawarga.clearFocus()
            binding.edEmailwarga.clearFocus()
            binding.edNotelpwarga.clearFocus()
            binding.edJeniskelaminwarga.clearFocus()
            validasiUpdateProfile()
        }
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_profile)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

        btnSimpan.setOnClickListener {
            dialog.dismiss()
            val direction = EditProfileDirections.actionEditProfileToAkunWarga()
            findNavController().navigate(direction)
        }

        dialog.show()
    }

    private fun validasiUpdateProfile(){
        val validNama = !binding.edNamawarga.text.isNullOrEmpty()
        val validEmail = !binding.edEmailwarga.text.isNullOrEmpty()
        val validNoHp = !binding.edNotelpwarga.text.isNullOrEmpty()
        val validGender = !binding.edJeniskelaminwarga.text.isNullOrEmpty()

        if (validNama && validEmail && validNoHp && validGender){
            if (imageUri != null){
                uploadImage()
            }
            updateProfile()
        } else {
            if (!validNama){
                binding.edNamawarga.error = "Masukkan nama!"
            }
            if (!validEmail){
                binding.edEmailwarga.error = "Masukkan email!"
            }
            if (!validNoHp){
                binding.edNotelpwarga.error = "Masukkan nomor handphone!"
            }
            if (!validGender){
                binding.edJeniskelaminwarga.error = "Masukan jenis kelamin!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!",Toast.LENGTH_LONG).show()
        }
    }

    private fun updateProfile() {
        WargaEditProfilePresenter(this).updateProfile(
            getToken(),
            idWarga,
            gender,
            noHp,
            nama,
            email,
            gambarWarga
        )
    }

    private fun namaFocusListener() {
        binding.edNamawarga.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNamawarga.error = validNamaWarga()
            }
        }
    }

    private fun validNamaWarga(): String? {
        nama = binding.edNamawarga.text.toString().trim()
        if (nama.isEmpty()){
            return "Masukkan nama!"
        }
        return null
    }

    private fun noHpFocusListener() {
        binding.edNotelpwarga.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNotelpwarga.error = validNoHpWarga()
            }
        }
    }

    private fun validNoHpWarga(): String? {
        noHp = binding.edNotelpwarga.text.toString().trim()
        if (noHp.isEmpty()){
            return "Masukkan nomor handphone!"
        }
        return null
    }

    private fun emailFocusListener() {
        binding.edEmailwarga.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edEmailwarga.error = validEmailWarga()
            }
        }
    }

    private fun validEmailWarga(): String? {
        email = binding.edEmailwarga.text.toString().trim()
        if (email.isEmpty()){
            return "Masukkan email!"
        }
        return null
    }

    private fun genderFocusListener() {
        binding.edJeniskelaminwarga.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edJeniskelaminwarga.error = validGenderWarga()
            }
        }
    }

    private fun validGenderWarga(): String? {
        gender = binding.edJeniskelaminwarga.text.toString().trim()
        if (gender.isEmpty()){
            return "Masukkan jenis kelamin!"
        }
        return null
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    override fun onUpdateSuccess(message: String) {
        dialogSukses()
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetDataSuccess(list: GetMe?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailed(message: String) {
        TODO("Not yet implemented")
    }


}