package com.maluku.sma_rt.view

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
import com.maluku.sma_rt.presenter.AdminRTLoginPresenter
import com.maluku.sma_rt.presenter.AdminRTRegisterPresenter
import com.maluku.sma_rt.view.viewInterface.LoginAdminInterface

class LoginRT : Fragment(), LoginAdminInterface {
    private lateinit var binding: FragmentLoginRTBinding
    private lateinit var emailAdmin: String
    private lateinit var passwordAdmin: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginRTBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnlgn.setOnClickListener {
            emailAdmin = binding.etLoginEmailAdmin.text.trim().toString()
            passwordAdmin = binding.etLoginPasswordAdmin.text.trim().toString()
            Log.d("TAG", "Login --> email: $emailAdmin; password: $passwordAdmin")
            loginAdmin(emailAdmin,passwordAdmin)
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
        navigateToAdminDashboard()
        Toast.makeText(context,"$message", Toast.LENGTH_LONG).show()
    }

    //navigate to admin dashboard
    private fun navigateToAdminDashboard() {
        findNavController().navigate(R.id.action_registerRT_to_homeFragment)
    }

}