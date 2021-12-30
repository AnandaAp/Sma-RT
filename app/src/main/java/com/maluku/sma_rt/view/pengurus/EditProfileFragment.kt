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
import android.widget.Spinner
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
import com.maluku.sma_rt.databinding.FragmentEditProfileRtBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.pengurus.GetPengurusById
import com.maluku.sma_rt.presenter.AdminRTProfilePresenter
import com.maluku.sma_rt.presenter.ListWargaPresenter
import com.maluku.sma_rt.view.viewInterface.AdminRTProfileInterface
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class EditProfileFragment : Fragment(), AdminRTProfileInterface {
    private lateinit var binding: FragmentEditProfileRtBinding
    private var id: String = ""
    private var nama: String = ""
    private var email: String = ""
    private var noHp: String = ""
    private var jenisKelamin: String = ""
    private var gambarPengurus: String = "default_image.jpg"
    private var imageUri: Uri? = null

    val args: EditProfileFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        namaFocusListener()
        emailFocusListener()
        noHPFocusListener()
        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                imageUri = it
                binding.ivEditPengurus.setImageURI(it)
            }
        )
        pickImage(getImage)
        savePengurusProfil()
        binding.btnBatalEdit.setOnClickListener {
            navigateEditProfilToAccount()
        }
    }

    private fun bindData() {
        id = args.pengurusId
        nama = args.namaPengurus
        email = args.emailPengurus
        jenisKelamin = args.jenisKelamin
        noHp = args.noHp
        gambarPengurus = args.gambarPengurus
        binding.etEditNama.setText(nama)
        binding.etEditEmail.setText(email)
        binding.etEditNoHP.setText(noHp)
        setGender()
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${gambarPengurus}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.ivEditPengurus)
        }.addOnFailureListener {

        }
    }

    private fun setGender(){
        val spGender = binding.etEditJenisKelamin
        spGender.isClickable = false
        spGender.isEnabled = false
        val listGender: ArrayList<String?> = arrayListOf("Laki-Laki", "Perempuan")
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item,listGender)
        spGender.adapter = adapter
        for(i in listGender.indices){
            if (listGender[i]?.lowercase() == jenisKelamin){
                spGender.setSelection(i)
            } else {
                spGender.setSelection(0)
            }
        }
    }

    private fun bindingView(): View? {
        binding = FragmentEditProfileRtBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun namaFocusListener() {
        binding.etEditNama.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILeditNama.helperText = validNama()
            }
        }
    }

    private fun validNama(): String? {
        nama = binding.etEditNama.text.toString().trim()
        if (nama.isEmpty()){
            return "Masukan nama!"
        }
        return null
    }

    private fun emailFocusListener() {
        binding.etEditEmail.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILeditEmail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        email = binding.etEditEmail.text.toString().trim()
        if (email.isEmpty()){
            return "Masukan email!"
        }
        return null
    }

    private fun noHPFocusListener() {
        binding.etEditNoHP.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILeditNoHP.helperText = validNoHp()
            }
        }
    }

    private fun validNoHp(): String? {
        noHp = binding.etEditNoHP.text.toString()
        if (noHp.isEmpty()){
            return "Masukan no HP!"
        }
        return null
    }

    private fun pickImage(getImage: ActivityResultLauncher<String>){
        binding.btnPickProfilImg.setOnClickListener {
            getImage.launch("image/*")
        }
    }

    private fun uploadImage(){
        val formatter = SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = "${nama}_${formatter.format(now)}"
        gambarPengurus = fileName
        val storageReference = FirebaseStorage.getInstance().getReference("user/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                binding.ivEditPengurus.setImageURI(null)
//                Toast.makeText(requireContext(),"Upload gambar sukses!",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(),"Upload gambar gagal!", Toast.LENGTH_LONG).show()
            }
    }

    private fun savePengurusProfil() {
        binding.btnUpdateProfil.setOnClickListener {
            binding.etEditNama.clearFocus()
            binding.etEditEmail.clearFocus()
            binding.etEditNoHP.clearFocus()
            binding.etEditJenisKelamin.clearFocus()
            validasiUpdatePengurus()
        }
    }

    private fun validasiUpdatePengurus(){
        val validNama = !binding.etEditNama.text.isNullOrEmpty()
        val validEmail = !binding.etEditEmail.text.isNullOrEmpty()
        val validNoHp = !binding.etEditNoHP.text.isNullOrEmpty()

        if (validNama && validEmail && validNoHp){
            if (imageUri != null){
                uploadImage()
            }
//            Log.d("EDIT_PROFILE","ID = $id, nama = $nama, email = $email, NoHP = $noHp, Gambar = $gambarPengurus, Gender = $jenisKelamin")
            updateProfile()
        } else {
            if (!validNama){
                binding.TILeditNama.helperText = "Masukkan nama!"
            }
            if (!validEmail){
                binding.TILeditEmail.helperText = "Masukkan email!"
            }
            if (!validNoHp){
                binding.TILeditNoHP.helperText = "Masukkan nomor handphone!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!",Toast.LENGTH_LONG).show()
        }
    }

    private fun updateProfile() {
        AdminRTProfilePresenter(this).updateProfilePengurus(
            getToken(),
            id,
            jenisKelamin,
            noHp,
            nama,
            email,
            gambarPengurus
        )
    }

    override fun onUpdateSuccess(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
        navigateEditProfilToAccount()
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetDataSuccess(result: GetPengurusById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    private fun navigateEditProfilToAccount() {
        findNavController().popBackStack()
    }

}