package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLupaPasswordRTBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.presenter.AdminRTPasswordPresenter
import com.maluku.sma_rt.view.viewInterface.AdminRTPasswordInterface

class LupaPasswordRTFragment : Fragment(), AdminRTPasswordInterface {
    private lateinit var binding: FragmentLupaPasswordRTBinding
    private var email: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailFocusListener()
        lupaPassword()
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
        binding = FragmentLupaPasswordRTBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun lupaPassword(){
        binding.btnLupaPass.setOnClickListener {
            binding.etEmail.clearFocus()
            submitForm()
        }
    }

    private fun submitForm() {
        val validEmail = !binding.etEmail.text.isNullOrEmpty()
        if (validEmail){
            AdminRTPasswordPresenter(this).forgetPassAdmin(getToken(),email)
        } else {
            if (!validEmail){
                binding.TILemail.helperText = "Masukan email Anda!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun emailFocusListener() {
        binding.etEmail.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILemail.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        email = binding.etEmail.text.toString().trim()
        var isValidEmail = UserValidator.isEmailValid(email)
        if (email.isEmpty()){
            return "Masukan email!"
        }
        if (!isValidEmail){
            return "Email tidak valid!"
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
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_lupaPasswordRTFragment2_to_resetPasswordRTFragment2)
    }

    override fun onForgetPassFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    override fun onResetPassSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResetPassFailure(message: String) {
        TODO("Not yet implemented")
    }
}