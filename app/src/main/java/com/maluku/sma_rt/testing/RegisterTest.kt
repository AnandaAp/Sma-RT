package com.maluku.sma_rt.testing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maluku.sma_rt.databinding.FragmentRegisterTestBinding
import com.maluku.sma_rt.presenter.WargaRegisterPresenter
import com.maluku.sma_rt.view.viewInterface.RegisterTestInterface

class RegisterTest : Fragment(),RegisterTestInterface {
    private lateinit var binding: FragmentRegisterTestBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterTestBinding.inflate(layoutInflater)
        register(
            "01FKKC90YY3ZWW6RCZZNG0G6SV",
            "test user",
            "awokokokooK1",
            "testUser@gmail.com",
            "laki-laki",
            "08971283761234")
        return binding.root
    }

    override fun onRegisterSuccess(message: String) {
        binding.registerTestMessage.text = message
    }

    override fun register(
        idKeluarga: String,
        name: String,
        password: String,
        email: String,
        gender: String,
        noHP: String
    ) {
        WargaRegisterPresenter(requireActivity()).registerNewUser(
            idKeluarga,
            email,
            password,
            name,
            gender,
            noHP)
    }


}