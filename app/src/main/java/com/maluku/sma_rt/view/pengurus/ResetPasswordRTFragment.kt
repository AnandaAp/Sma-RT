package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentResetPasswordRTBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.presenter.AdminRTPasswordPresenter
import com.maluku.sma_rt.view.viewInterface.AdminRTPasswordInterface


class ResetPasswordRTFragment : Fragment(), AdminRTPasswordInterface {
    private lateinit var binding: FragmentResetPasswordRTBinding
    private var kodeVerif: String = ""
    private var passBaru: String = ""
    private var confirmPass: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kodeVerifFocusListener()
        passBaruFocusListener()
        confirmPassFocusListener()
        resetPassword()
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentResetPasswordRTBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun resetPassword(){
        binding.btnResetPass.setOnClickListener {
            binding.etKodeVerif.clearFocus()
            binding.etPassBaru.clearFocus()
            binding.etConfirmPass.clearFocus()
            submitForm()
        }
    }

    private fun submitForm() {
        val validKodeVerif = !binding.etKodeVerif.text.isNullOrEmpty()
        val validPassBaru = !binding.etPassBaru.text.isNullOrEmpty()
        val validConfirmPass = !binding.etConfirmPass.text.isNullOrEmpty()
        if (validKodeVerif && validConfirmPass && validPassBaru){
            if (passBaru != confirmPass){
                Log.d("RESET_PASS","Pass Baru: $passBaru || confirm = $confirmPass")
                binding.TILconfirmPass.helperText = "Password tidak sesuai!"
            } else {
                AdminRTPasswordPresenter(this).resetPassAdmin(getToken(),kodeVerif,passBaru)
            }
        } else {
            if (!validKodeVerif){
                binding.TILkodeVerif.helperText = "Masukan kode verifikasi!"
            }
            if (!validPassBaru){
                binding.TILpassBaru.helperText = "Masukan password baru!"
            }
            if (!validConfirmPass){
                binding.TILconfirmPass.helperText = "Masukan konfirmasi password!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun kodeVerifFocusListener() {
        binding.etKodeVerif.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILkodeVerif.helperText = validKodeVerif()
            }
        }
    }

    private fun validKodeVerif(): String? {
        kodeVerif = binding.etKodeVerif.text.toString().trim()
        if (kodeVerif.isEmpty()){
            return "Masukan kode verifikasi!"
        }
        return null
    }

    private fun passBaruFocusListener() {
        binding.etPassBaru.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILpassBaru.helperText = validPassBaru()
            }
        }
    }

    private fun validPassBaru(): String? {
        passBaru = binding.etPassBaru.text.toString().trim()
        if (passBaru.isEmpty()){
            return "Masukan password baru!"
        }
        return null
    }

    private fun confirmPassFocusListener() {
        binding.etConfirmPass.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILconfirmPass.helperText = validConfirmPass()
            }
        }
    }

    private fun validConfirmPass(): String? {
        confirmPass = binding.etConfirmPass.text.toString().trim()
        if (confirmPass.isEmpty()){
            return "Masukan konfirmasi password!"
        }
        return null
    }

    override fun onChangePassSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onChangePassFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgetPassSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgetPassFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResetPassSuccess(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_resetPasswordRTFragment2_to_loginRT)
    }

    override fun onResetPassFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

}