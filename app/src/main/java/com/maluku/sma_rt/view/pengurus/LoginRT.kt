package com.maluku.sma_rt.view.pengurus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginRTBinding
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.presenter.AdminRTLoginPresenter
import com.maluku.sma_rt.view.activity.DashboardRTActivity
import com.maluku.sma_rt.view.viewInterface.LoginAdminInterface

class LoginRT : Fragment(), LoginAdminInterface {
    private lateinit var binding: FragmentLoginRTBinding
    private lateinit var emailAdmin: String
    private lateinit var passwordAdmin: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToAdminRegister()
        emailFocusListener()
        passwordFocusListener()
        navigateToAdminRegister()
        binding.btnlgn.setOnClickListener {
            binding.etLoginEmailAdmin.clearFocus()
            binding.etLoginPasswordAdmin.clearFocus()
            submitForm()
        }
    }

    override fun loginAdmin(
        inputEmailAdmin: String,
        inputPasswordAdmin: String
    )
    {
        AdminRTLoginPresenter(requireActivity(),this).loginAdminPresenter(inputEmailAdmin,inputPasswordAdmin)
    }

    override fun onLoginSuccess(message: String) {
        Toast.makeText(context,"$message", Toast.LENGTH_LONG).show()
        navigateToAdminDashboard()
    }

    private fun submitForm() {
        val validEmail = !binding.etLoginEmailAdmin.text.isNullOrEmpty()
        val validPassword = !binding.etLoginPasswordAdmin.text.isNullOrEmpty()

        if (validEmail && validPassword){
            Log.d("TAG", "Email: $emailAdmin; password: $passwordAdmin;")
            loginAdmin(emailAdmin,passwordAdmin)
        } else {
            if (!validEmail){
                binding.etLoginEmailAdmin.error = "Masukan email!"
            }
            if (!validPassword){
                binding.etLoginPasswordAdmin.error = "Masukan password!"
            }
            Toast.makeText(requireContext(),"Login gagal!",Toast.LENGTH_LONG).show()
        }
    }

    private fun emailFocusListener() {
        binding.etLoginEmailAdmin.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etLoginEmailAdmin.error = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        emailAdmin = binding.etLoginEmailAdmin.text.toString().trim()
        var isValidEmail = UserValidator.isEmailValid(emailAdmin)
        if (emailAdmin.isEmpty()){
            return "Masukan email!"
        }
        if (!isValidEmail){
            return "Email tidak valid!"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.etLoginPasswordAdmin.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etLoginPasswordAdmin.error = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        passwordAdmin = binding.etLoginPasswordAdmin.text.toString()
        if (passwordAdmin.isEmpty()){
            return "Masukan password!"
        }
        return null
    }

    private fun bindingView(): View {
        binding = FragmentLoginRTBinding.inflate(layoutInflater)
        return binding.root
    }

    //navigate to register admin
    private fun navigateToAdminRegister() {
        binding.btndaftar.setOnClickListener{
            findNavController().navigate(R.id.action_loginRT_to_registerRT)
        }
    }

    //navigate to admin dashboards
    private fun navigateToAdminDashboard() {
        Thread.sleep(1000)
        val intent = Intent(
            requireActivity(),
            DashboardRTActivity::class.java
        )
        startActivity(intent)
        requireActivity().finish()
    }

}