package com.maluku.sma_rt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.maluku.sma_rt.databinding.FragmentLoginRTBinding
import com.maluku.sma_rt.databinding.FragmentRegisterRTBinding


class Login_RT : Fragment() {
    private lateinit var binding: FragmentLoginRTBinding
    private lateinit var inputUsernameAdmin: String
    private lateinit var inputPasswordAdmin: String

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
        inputUsernameAdmin = binding.inputUsernameAdmin.toString()
        inputPasswordAdmin = binding.inputPasswordAdmin.toString()
    }
}