package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding
import com.maluku.sma_rt.databinding.FragmentLupaPasswordBinding
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.ProdukPresenter
import com.maluku.sma_rt.presenter.WargaPresenter
import com.maluku.sma_rt.view.viewInterface.WargaInterface


class LupaPassword : Fragment(), WargaInterface {

    private lateinit var binding: FragmentLupaPasswordBinding

    private var email: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnBack()
        emailFocusListener()
        btnSubmit()
    }

    private fun bindingView(): View {
        binding = FragmentLupaPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_lupaPassword_to_loginWarga)
        }
    }

    private fun emailFocusListener() {
        binding.edEmailwarga.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edEmailwarga.error = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        email = binding.edEmailwarga.text.toString().trim()
        if (email.isEmpty()){
            return "Masukan email Anda!"
        }
        return null
    }

    private fun btnSubmit() {
        binding.btnSubmit.setOnClickListener{
            binding.edEmailwarga.clearFocus()
            validasiTambahProduk()
        }
    }

    private fun validasiTambahProduk(){
        val validEmail = !binding.edEmailwarga.text.isNullOrEmpty()

        if (validEmail){
            forgetPassword()
        } else {
            if (!validEmail){
                binding.edEmailwarga.error = "Masukan email Anda!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun forgetPassword() {
        WargaPresenter(requireActivity(),this).forgetPassword(
            email
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
        TODO("Not yet implemented")
    }

    override fun onChangePasswordFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgetPasswordSuccess(message: String) {
        Toast.makeText(context,"Silakan cek e-Mail Anda!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_lupaPassword_to_resetPassword)
    }

    override fun onForgetPasswordFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
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
