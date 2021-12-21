package com.maluku.sma_rt.view.warga

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding
import com.maluku.sma_rt.presenter.WargaLoginPresenter
import com.maluku.sma_rt.view.activity.DashboardWargaActivity
import com.maluku.sma_rt.view.viewInterface.LoginWargaInterface

class LoginWarga : Fragment(), LoginWargaInterface{

    private lateinit var binding: FragmentLoginWargaBinding
    private var email: String = ""
    private var password : String = ""
    private var validLogin: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDaftarNavigateToRegisterWarga()
        btnLoginOnLogin()
        forgotPassword()


    }

    private fun bindingView(): View {
        binding = FragmentLoginWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun btnDaftarNavigateToRegisterWarga() {
        binding.btnDaftar.setOnClickListener{
            findNavController().navigate(R.id.action_loginWarga_to_registerWarga)
        }
    }

    private fun btnLoginOnLogin() {
        binding.btnLogin.setOnClickListener {
            setLoginData()
            validasiData()
            if(validLogin) {
                loginWarga(
                    email,
                    password
                )
            }
        }
    }

    private fun validasiData() {
        validLogin = true
        if(email.isEmpty()) {
            binding.edUsername.error = "Masukkan email!"
            validLogin = false
        }
        if(password.isEmpty()) {
            binding.edPassword.error = "Masukkan password!"
            validLogin = false
        }

    }

    private fun setLoginData() {
        email = binding.edUsername.text!!.trim().toString()
        password = binding.edPassword.text!!.trim().toString()
    }


    override fun loginWarga(email: String, password: String) {
        WargaLoginPresenter(requireActivity(), this)
            .loginUser(
                email,
                password
            )
    }

    private fun forgotPassword() {
        binding.tvLupapass.setOnClickListener{
            findNavController().navigate(R.id.action_loginWarga_to_lupaPassword)
        }
    }

    private fun navigateToDashboard() {
        Thread.sleep(1000)
        val intent = Intent(
            requireActivity(),
            DashboardWargaActivity::class.java
        )
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onLoginSuccess(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        navigateToDashboard()

    }
}