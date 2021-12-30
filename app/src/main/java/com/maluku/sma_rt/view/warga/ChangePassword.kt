package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentChangePasswordBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.WargaPresenter
import com.maluku.sma_rt.view.viewInterface.WargaInterface
import java.io.File

private const val TAG = "CHANGE PASSWORD"

class ChangePassword : Fragment(), WargaInterface {
    val args: ChangePasswordArgs by navArgs()

    private lateinit var binding: FragmentChangePasswordBinding

    private var idWarga: String = ""
    private var nama: String = ""
    private var noHp: String = ""
    private var email: String = ""
    private var gender: String = ""
    private var gambarWarga: String = "default_image"
    private var password: String = ""

    private var passwordLama: String = ""
    private var passwordBaru: String = ""
    private var passwordBaru2: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        passwordLamaFocusListener()
        passwordBaruFocusListener()
        passwordBaru2FocusListener()
        saveProfile()
        goBack()

    }

    private fun saveProfile() {
        binding.btnSimpan.setOnClickListener {
            binding.edPasslama.clearFocus()
            binding.edPassbaru.clearFocus()
            binding.edKonfpassbaru.clearFocus()
            validasiUpdateProfile()
        }
    }

    private fun validasiUpdateProfile(){
        val validPasswordLama = !binding.edPasslama.text.isNullOrEmpty()
        val validPasswordBaru = !binding.edPassbaru.text.isNullOrEmpty()
        val validPasswordBaru2 = !binding.edKonfpassbaru.text.isNullOrEmpty()

        if (validPasswordLama && validPasswordBaru && validPasswordBaru2){
            if(passwordBaru == passwordBaru2) {
                gantiPassword()
            } else {
                Toast.makeText(activity,"Password baru tidak sama!", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (!validPasswordLama){
                binding.edPasslama.error = "Masukkan password lama!"
            }
            if (!validPasswordBaru){
                binding.edPassbaru.error = "Masukkan password baru!"
            }
            if (!validPasswordBaru2){
                binding.edKonfpassbaru.error = "Masukkan konfirmasi password baru!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!",Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun gantiPassword() {
        WargaPresenter(requireActivity(),this).changePassword(
            getToken(),
            passwordLama,
            passwordBaru
        )
    }

    private fun bindData() {
        idWarga = args.wargaId
        nama = args.wargaNama
        noHp = args.wargaNohp
        email = args.wargaEmail
        gender = args.wargaGender
        gambarWarga = args.wargaGambar
        password = args.wargaPassword
    }

    private fun passwordLamaFocusListener() {
        binding.edPasslama.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edPasslama.error = validPasswordLama()
            }
        }
    }

    private fun validPasswordLama(): String? {
        passwordLama = binding.edPasslama.text.toString().trim()
        if (passwordLama.isEmpty()){
            return "Masukkan password lama!"
        }
        return null
    }

    private fun passwordBaruFocusListener() {
        binding.edPassbaru.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edPassbaru.error = validPasswordBaru()
            }
        }
    }

    private fun validPasswordBaru(): String? {
        passwordBaru = binding.edPassbaru.text.toString().trim()
        if (passwordBaru.isEmpty()){
            return "Masukkan password baru!"
        }
        return null
    }

    private fun passwordBaru2FocusListener() {
        binding.edKonfpassbaru.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edKonfpassbaru.error = validPasswordBaru2()
            }
        }
    }

    private fun validPasswordBaru2(): String? {
        passwordBaru2 = binding.edKonfpassbaru.text.toString().trim()
        if (passwordBaru2.isEmpty()){
            return "Masukkan konfirmasi password baru!"
        }
        return null
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_profile)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

        btnSimpan.setOnClickListener {
            dialog.dismiss()
            val direction = ChangePasswordDirections.actionChangePasswordToHomeWarga()
            findNavController().navigate(direction)
        }

        dialog.show()
    }


    private fun bindingView(): View {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_changePassword_to_akunWarga)
        }
        binding.btnBatal.setOnClickListener{
            findNavController().navigate(R.id.action_changePassword_to_akunWarga)
        }
    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRegisterSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRegisterFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onChangePasswordSuccess(message: String) {
        dialogSukses()
    }

    override fun onChangePasswordFailure(message: String) {
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }

    override fun onUpdateSuccess(message: String) {
    }

    override fun onUpdateFailure(message: String) {
    }

    override fun onForgetPasswordSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgetPasswordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResetPasswordSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResetPasswordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(result: GetMe?) {
    }

    override fun onGetDataFailure(message: String) {
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }

}