package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLupaPasswordBinding
import com.maluku.sma_rt.databinding.FragmentResetPasswordBinding
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.WargaPresenter
import com.maluku.sma_rt.view.viewInterface.WargaInterface

class ResetPassword : Fragment(), WargaInterface {

    private lateinit var binding: FragmentResetPasswordBinding

    private var kode: String = ""
    private var password: String = ""
    private var password2: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        kodeFocusListener()
        passwordFocusListener()
        password2FocusListener()
        btnSubmit()
    }

    private fun bindingView(): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun kodeFocusListener() {
        binding.edKodeverif.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edKodeverif.error = validKode()
            }
        }
    }

    private fun validKode(): String? {
        kode = binding.edKodeverif.text.toString().trim()
        if (kode.isEmpty()){
            return "Masukan kode verifikasi!"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.edNewpass.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNewpass.error = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        password = binding.edNewpass.text.toString().trim()
        if (password.isEmpty()){
            return "Masukan password baru!"
        }
        return null
    }

    private fun password2FocusListener() {
        binding.edConfirmpass.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edConfirmpass.error = validPassword2()
            }
        }
    }

    private fun validPassword2(): String? {
        password2 = binding.edConfirmpass.text.toString().trim()
        if (password2.isEmpty()){
            return "Masukan konfirmasi password baru!"
        }
        return null
    }

    private fun btnSubmit() {
        binding.btnSubmit.setOnClickListener{
            binding.edKodeverif.clearFocus()
            binding.edNewpass.clearFocus()
            binding.edConfirmpass.clearFocus()
            validasiResetPassword()
        }
    }

    private fun validasiResetPassword(){
        val validKode = !binding.edKodeverif.text.isNullOrEmpty()
        val validPassword = !binding.edNewpass.text.isNullOrEmpty()
        val validPassword2 = !binding.edConfirmpass.text.isNullOrEmpty()

        if (validKode && validPassword && validPassword2){
            if(password == password2) {
                resetPassword()
            } else {
                Toast.makeText(activity,"Password baru tidak sama!", Toast.LENGTH_SHORT).show()
            }
        } else {
            if (!validKode){
                binding.edKodeverif.error = "Masukan kode verifikasi!"
            }
            if (!validPassword){
                binding.edNewpass.error = "Masukan password baru!"
            }
            if (!validPassword2){
                binding.edConfirmpass.error = "Masukan konfirmasi password baru!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun resetPassword() {
        WargaPresenter(requireActivity(),this).resetPassword(
            kode, password
        )
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
        Toast.makeText(requireContext(),"Password berhasil diganti! Silakan login kembali!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_resetPassword_to_loginWarga)
    }

    override fun onChangePasswordFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
    }

}