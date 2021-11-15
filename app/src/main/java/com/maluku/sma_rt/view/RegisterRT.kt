package com.maluku.sma_rt.view

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRegisterRTBinding
import com.maluku.sma_rt.presenter.AdminRTRegisterPresenter
import com.maluku.sma_rt.view.viewInterface.RegisterAdminInterface
import java.util.regex.Pattern

class RegisterRT : Fragment(), RegisterAdminInterface {
    private lateinit var binding: FragmentRegisterRTBinding
    private  var kodeRT: String = ""
    private  var genderAdmin: String = ""
    private  var noHpAdmin: String = ""
    private  var namaAdmin: String = ""
    private  var emailAdmin: String = ""
    private  var password: String = ""
    private  var confirmPassword: String = ""
    private var validRegister: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterRTBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genderList = listOf("Laki-Laki", "Perempuan")
        val adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item,genderList)
        binding.spGenderAdmin.adapter = adapter
        binding.spGenderAdmin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, positiion: Int, id: Long) {
                genderAdmin = adapterView?.getItemAtPosition(positiion).toString().lowercase().trim()
                Toast.makeText(context,"Gender: $genderAdmin", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        binding.btnDaftar.setOnClickListener {
            password = binding.inputPassword.text.trim().toString()
            confirmPassword = binding.inputConfirmPassword.text.trim().toString()
            namaAdmin = binding.inputNamaAdmin.text.trim().toString()
            noHpAdmin = binding.inputNoHpAdmin.text.trim().toString()
            emailAdmin = binding.inputEmailAdmin.text.trim().toString()
            kodeRT = binding.inputKodeRT.text.trim().toString()
            if (namaAdmin.isEmpty()){
                binding.inputNamaAdmin.error = "Masukan username!"
                return@setOnClickListener
            } else if (emailAdmin.isEmpty()){
                binding.inputEmailAdmin.error = "Masukan email!"
                return@setOnClickListener
            } else if (noHpAdmin.isEmpty()){
                binding.inputNoHpAdmin.error = "Masukan No HP!"
                return@setOnClickListener
            } else if (password.isEmpty()){
                binding.inputPassword.error = "Masukan password!"
                return@setOnClickListener
            } else if (confirmPassword.isEmpty()){
                binding.inputConfirmPassword.error = "Masukan konfirmasi password!"
                return@setOnClickListener
            } else if (kodeRT.isEmpty()){
                binding.inputKodeRT.error = "Masukan kode RT!"
                return@setOnClickListener
            } else {
                if (!binding.checkBoxsk.isChecked){
                    validRegister = false
                    Toast.makeText(context,"Anda harus setuju dengan syarat dan ketentuan yang berlaku!",Toast.LENGTH_LONG).show()
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailAdmin).matches()) {
                    validRegister = false
                    binding.inputEmailAdmin.error = "Email tidak valid!"
                    return@setOnClickListener
                }
                if (isValidPassword(password)) {
                    if (password != confirmPassword){
                        validRegister = false
                        binding.inputConfirmPassword.error = "Password tidak sesuai!"
                        return@setOnClickListener
                    }
                } else {
                    binding.inputPassword.error = "Password panjangnya min. 8 karakter, serta mengandung min. 1 huruf besar, 1 huruf kecil, dan 1 angka!"
                }
                if (validRegister){
                    Log.d("TAG", "nama: $namaAdmin; no hp: $noHpAdmin; email: $emailAdmin; kode: $kodeRT; password: $password; confirm pass: $confirmPassword;")
                    registerAdmin(kodeRT,genderAdmin, noHpAdmin,namaAdmin, emailAdmin, password)
                }
            }
        }
    }

    fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
            val passwordMatcher = Regex(passwordPattern)
            return passwordMatcher.find(password) != null
        } ?: return false
    }

    override fun registerAdmin(
        inputKodeRT: String,
        inputGenderAdmin: String,
        inputNoHpAdmin: String,
        inputNamaAdmin: String,
        inputEmailAdmin: String,
        inputPassword: String
    ) {
        AdminRTRegisterPresenter(requireActivity(), this).registerNewAdmin(inputKodeRT,inputGenderAdmin,inputNoHpAdmin,inputNamaAdmin,inputEmailAdmin,inputPassword)
    }

    override fun onRegisterSuccess(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
    }
}