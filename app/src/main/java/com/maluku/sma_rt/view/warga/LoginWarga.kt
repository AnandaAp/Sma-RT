package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding
import com.maluku.sma_rt.presenter.WargaLoginPresenter
import com.maluku.sma_rt.presenter.WargaRegisterPresenter
import com.maluku.sma_rt.view.viewInterface.LoginWargaInterface

class LoginWarga : Fragment(), LoginWargaInterface{

    private lateinit var binding: FragmentLoginWargaBinding
    private var email: String = ""
    private var password : String = ""
    private var validLogin: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnDaftarNavigateToRegisterWarga()
        btnLoginOnLogin()


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
        WargaLoginPresenter(requireActivity())
            .loginUser(
                email,
                password
            )
    }

    override fun onLoginSuccess(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}