package com.maluku.sma_rt.view.warga

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.WargaPresenter
import com.maluku.sma_rt.view.activity.DashboardWargaActivity
import com.maluku.sma_rt.view.viewInterface.WargaInterface

private var token_firebase = ""
class LoginWarga : Fragment(), WargaInterface{

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
        getFCMToken()
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


    fun loginWarga(email: String, password: String) {
        WargaPresenter(requireActivity(), this)
            .login(
                email,
                password,
                token_firebase
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

    override fun onLoginFailure(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
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

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get FCM token
            val token = task.result
            token?.let { Log.d(ContentValues.TAG, it) }
            // Isi token device saat ini
            token_firebase = token.toString()
            Log.d("WARGA", "Token Login Warga saat ini: $token_firebase")
            Log.d(ContentValues.TAG, "Token saat ini: $token")
        })
    }
}