package com.maluku.sma_rt.view.pengurus

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
import com.maluku.sma_rt.databinding.FragmentLoginRTBinding
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.presenter.AdminRTLoginPresenter
import com.maluku.sma_rt.view.activity.DashboardRTActivity
import com.maluku.sma_rt.view.viewInterface.LoginAdminInterface


class LoginRT : Fragment(), LoginAdminInterface {
    private lateinit var binding: FragmentLoginRTBinding
    private var emailAdmin: String = ""
    private var passwordAdmin: String = ""
    private var token_firebase: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToAdminRegister()
        emailFocusListener()
        passwordFocusListener()
        navigateToAdminRegister()
        navigateLoginToLupaPassword()
        getFCMToken()
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
        if (context!=null){
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            navigateToAdminDashboard()
        }

    }

    override fun onLoginFailure(message: String) {
        if (context!=null){
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    private fun submitForm() {
        val validEmail = !binding.etLoginEmailAdmin.text.isNullOrEmpty()
        val validPassword = !binding.etLoginPasswordAdmin.text.isNullOrEmpty()

        if (validEmail && validPassword){
            Log.d("TAG", "Email: $emailAdmin; password: $passwordAdmin;")
            loginAdmin(emailAdmin,passwordAdmin)
        } else {
            if (!validEmail){
                binding.TILinputEmailAdmin.helperText = "Masukan email!"
            }
            if (!validPassword){
                binding.TILinputPassword.helperText = "Masukan password!"
            }
            Toast.makeText(requireContext(),"Login gagal!",Toast.LENGTH_LONG).show()
        }
    }

    private fun emailFocusListener() {
        binding.etLoginEmailAdmin.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputEmailAdmin.helperText = validEmail()
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
                binding.TILinputPassword.helperText = validPassword()
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

    private fun navigateLoginToLupaPassword(){
        binding.btnToLupaPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginRT_to_lupaPasswordRTFragment2)
        }
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
            Log.d(ContentValues.TAG, "Token saat ini: $token")
        })
    }

}