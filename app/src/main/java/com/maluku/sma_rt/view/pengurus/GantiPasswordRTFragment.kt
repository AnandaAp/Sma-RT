package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentGantiPasswordRtBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.presenter.AdminRTPasswordPresenter
import com.maluku.sma_rt.view.viewInterface.AdminRTPasswordInterface


class GantiPasswordRTFragment : Fragment(), AdminRTPasswordInterface {
    private lateinit var binding: FragmentGantiPasswordRtBinding
    private var passLama: String = ""
    private var passBaru: String = ""
    private var confirmPass: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passLamaFocusListener()
        passBaruFocusListener()
        confirmPassFocusListener()
        gantiPassword()
        binding.btnBatal.setOnClickListener {
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
        binding = FragmentGantiPasswordRtBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun gantiPassword(){
        binding.btnGantiPass.setOnClickListener {
            binding.etPassLama.clearFocus()
            binding.etPassBaru.clearFocus()
            binding.etConfirmPass.clearFocus()
            submitForm()
        }
    }

    private fun submitForm() {
        val validPassLama = !binding.etPassLama.text.isNullOrEmpty()
        val validPassBaru = !binding.etPassBaru.text.isNullOrEmpty()
        val validConfirmPass = !binding.etConfirmPass.text.isNullOrEmpty()
        if (validPassLama && validConfirmPass && validPassBaru){
            if (passBaru != confirmPass){
                Log.d("GANTI_PASS","Pass Baru: $passBaru || confirm = $confirmPass")
                binding.TILconfirmPass.helperText = "Password tidak sesuai!"
            } else {
                AdminRTPasswordPresenter(this).changePassAdmin(getToken(),passLama,passBaru)
            }
        } else {
            if (!validPassLama){
                binding.TILpassLama.helperText = "Masukan password lama!"
            }
            if (!validPassBaru){
                binding.TILpassBaru.helperText = "Masukan password baru!"
            }
            if (!validConfirmPass){
                binding.TILconfirmPass.helperText = "Masukan konfirmasi password!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun passLamaFocusListener() {
        binding.etPassLama.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILpassLama.helperText = validPassLama()
            }
        }
    }

    private fun validPassLama(): String? {
        passLama = binding.etPassLama.text.toString().trim()
        if (passLama.isEmpty()){
            return "Masukan password lama!"
        }
        return null
    }

    private fun passBaruFocusListener() {
        binding.etPassBaru.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILpassBaru.helperText = validPassBaru()
            }
        }
    }

    private fun validPassBaru(): String? {
        passBaru = binding.etPassBaru.text.toString().trim()
        if (passBaru.isEmpty()){
            return "Masukan password baru!"
        }
        return null
    }

    private fun confirmPassFocusListener() {
        binding.etConfirmPass.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILconfirmPass.helperText = validConfirmPass()
            }
        }
    }

    private fun validConfirmPass(): String? {
        confirmPass = binding.etConfirmPass.text.toString().trim()
        if (confirmPass.isEmpty()){
            return "Masukan konfirmasi password!"
        }
        return null
    }

    override fun onChangePassSuccess(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
        navigateGantiPassToAkun()
    }

    override fun onChangePassFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    override fun onForgetPassSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onForgetPassFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResetPassSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onResetPassFailure(message: String) {
        TODO("Not yet implemented")
    }

    private fun navigateGantiPassToAkun(){
        findNavController().navigate(R.id.action_gantiPasswordRTFragment_to_navigation_profil)
    }

}