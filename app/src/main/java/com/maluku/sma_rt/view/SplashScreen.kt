package com.maluku.sma_rt.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentSplashScreenBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_EMAIL_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_FAMILY_ID_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_GENDER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ID_RT_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_NAME_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PHONE_NUMBER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ROLE_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.view.activity.DashboardRTActivity
import com.maluku.sma_rt.view.activity.DashboardWargaActivity

class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private var isLogin: Boolean = false
    private var role: String = ""
    private var kodeKeluarga: String = ""
    private var email: String = ""
    private var password: String = ""
    private var nama: String = ""
    private var gender: String = ""
    private var noHP: String = ""
    private var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSessions()
        navigateToDashboard()
    }

    private fun bindView(): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return  binding.root
    }

    private fun checkSessions() {
        val preferences = UserSession(requireActivity())
        token = preferences
            .getValueString(SHARED_PREFERENCE_TOKEN_KEY)
        if (!token.isNullOrEmpty()){
            isLogin = true
        }
        if(isLogin){
            role = preferences.getValueString(SHARED_PREFERENCE_ROLE_KEY)
            if (role == "pengurus"){
                kodeKeluarga = preferences
                    .getValueString(SHARED_PREFERENCE_ID_RT_KEY)
            } else if(role == "warga"){
                kodeKeluarga = preferences
                    .getValueString(SHARED_PREFERENCE_FAMILY_ID_KEY)
            }
            email = preferences
                .getValueString(SHARED_PREFERENCE_EMAIL_KEY)
            password = preferences
                .getValueString(SHARED_PREFERENCE_PASSWORD_KEY)
            nama = preferences
                .getValueString(SHARED_PREFERENCE_NAME_KEY)
            gender = preferences
                .getValueString(SHARED_PREFERENCE_GENDER_KEY)
            noHP = preferences
                .getValueString(SHARED_PREFERENCE_PHONE_NUMBER_KEY)
        }
    }

    private fun navigateToDashboard() {
        if(isLogin){
            if (role == "pengurus"){
                Handler().postDelayed({
                    val intent = Intent(requireActivity(), DashboardRTActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                },4000)
            } else if(role == "warga"){
                Handler().postDelayed({
                    val intent = Intent(requireActivity(), DashboardWargaActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                },4000)
            }
        } else {
            Handler().postDelayed({
                findNavController().navigate(R.id.action_splashScreen_to_loginChoice)
            }, 3000)
        }
    }
}