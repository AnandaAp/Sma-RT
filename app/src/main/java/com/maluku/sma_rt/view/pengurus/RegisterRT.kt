package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRegisterRTBinding
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.presenter.AdminRTRegisterPresenter
import com.maluku.sma_rt.view.viewInterface.RegisterAdminInterface

class RegisterRT : Fragment(), RegisterAdminInterface {
    private lateinit var binding: FragmentRegisterRTBinding
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private  var kodeRT: String = ""
    private  var genderAdmin: String = ""
    private  var noHpAdmin: String = ""
    private  var namaAdmin: String = ""
    private  var emailAdmin: String = ""
    private  var password: String = ""
    private  var confirmPassword: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFormJenisKelamin()
        nameFocusListener()
        emailFocusListener()
        passwordFocusListener()
        codeRTFocusListener()
        confirmPasswordFocusListener()
        numPhoneFocusListener()
        btnLoginNavigateToLoginAdmin()
        binding.btnDaftar.setOnClickListener {
            binding.inputNamaAdmin.clearFocus()
            binding.inputEmailAdmin.clearFocus()
            binding.spGenderAdmin.clearFocus()
            binding.inputNoHpAdmin.clearFocus()
            binding.inputPassword.clearFocus()
            binding.inputConfirmPassword.clearFocus()
            binding.inputKodeRT.clearFocus()
            submitForm()
        }
    }

    private fun submitForm(){
        val validName = !binding.inputNamaAdmin.text.isNullOrEmpty()
        val validEmail = !binding.inputEmailAdmin.text.isNullOrEmpty()
        val validPassword = !binding.inputPassword.text.isNullOrEmpty()
        val validConfirmPassword = !binding.inputConfirmPassword.text.isNullOrEmpty()
        val validKodeRT = !binding.inputKodeRT.text.isNullOrEmpty()
        val validNoHp = !binding.inputNoHpAdmin.text.isNullOrEmpty()
        val validGender = !genderAdmin.isNullOrEmpty()
        if (validName && validEmail && validPassword && validConfirmPassword && validGender && validKodeRT && validNoHp){
            if (password != confirmPassword){
                binding.TILinputConfirmPassword.helperText = "Password tidak sesuai!"
            } else {
                Log.d("TAG", "kode RT: $kodeRT; Email: $emailAdmin; password: $password; nama: $namaAdmin; gender: $genderAdmin")
                registerAdmin(kodeRT,genderAdmin,noHpAdmin,namaAdmin,emailAdmin,password)
            }
        } else {
            if (!validName){
                binding.TILinputNamaAdmin.helperText = "Masukan nama!"
            }
            if (!validGender){
                binding.TILspGenderAdmin.helperText = "Pilih jenis kelamin!"
            }
            if (!validEmail){
                binding.TILinputEmailAdmin.helperText = "Masukan email!"
            }
            if (!validNoHp){
                binding.TILinputNoHpAdmin.helperText = "Masukan nomor HP!"
            }
            if (!validPassword){
                binding.TILinputPassword.helperText = "Masukan password!"
            }
            if (!validConfirmPassword){
                binding.TILinputConfirmPassword.helperText = "Masukan konfirmasi password!"
            }
            if (!validKodeRT){
                binding.TILinputKodeRT.helperText = "Masukan kode RT!"
            }
            Toast.makeText(requireContext(),"Registrasi gagal!",Toast.LENGTH_LONG).show()
        }
    }

    private fun nameFocusListener(){
        binding.inputNamaAdmin.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputNamaAdmin.helperText = validName()
            }
        }
    }

    private fun numPhoneFocusListener(){
        binding.inputNoHpAdmin.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputNoHpAdmin.helperText = validNoHp()
            }
        }
    }

    private fun emailFocusListener(){
        binding.inputEmailAdmin.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputEmailAdmin.helperText = validEmail()
            }
        }
    }

    private fun passwordFocusListener(){
        binding.inputPassword.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputPassword.helperText = validPassword()
            }
        }
    }

    private fun confirmPasswordFocusListener(){
        binding.inputConfirmPassword.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputConfirmPassword.helperText = validConfirmPassword()
            }
        }
    }

    private fun codeRTFocusListener(){
        binding.inputKodeRT.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.TILinputKodeRT.helperText = validRTCode()
            }
        }
    }

    private fun validConfirmPassword(): String? {
        confirmPassword = binding.inputConfirmPassword.text.toString().trim()
        if (confirmPassword.isEmpty()){
            return "Masukan konfirmasi password!"
        }
        return null
    }

    private fun validRTCode():String?{
        kodeRT = binding.inputKodeRT.text.toString().trim()
        if (kodeRT.isEmpty()){
            return "Masukan kode RT!"
        }
        return null
    }

    private fun validNoHp(): String? {
        noHpAdmin = binding.inputNoHpAdmin.text.toString().trim()
        if (noHpAdmin.isEmpty()){
            return "Masukan nomor handphone!"
        }
        return null
    }

    private fun validEmail(): String?{
        emailAdmin = binding.inputEmailAdmin.text.toString().trim()
        var isValidEmail = UserValidator.isEmailValid(emailAdmin)
        if (emailAdmin.isEmpty()){
            return "Masukan email!"
        }
        if (!isValidEmail){
            return "Email tidak valid!"
        }
        return null
    }

    private fun validName():String? {
        namaAdmin = binding.inputNamaAdmin.text.toString().trim()
        var isValidName = UserValidator.isNameValid(namaAdmin)
        var isNameIncludeLetters = UserValidator.isNameIncludeLetters(namaAdmin)
        if (namaAdmin.isEmpty()){
            return "Masukan nama!"
        }
        if (!isValidName){
            if (!isNameIncludeLetters){
                return "Nama harus mengandung karakter!"
            }
        }
        return null
    }

    private fun validPassword(): String?{
        password = binding.inputPassword.text.toString()
        var isPasswordValid = UserValidator.isPasswordsValid(password)
        var isPasswordLengthValid = UserValidator.passwordLengthValidator(password)
        var isPasswordWithNum = UserValidator.isPasswordWithNumber(password)
        var isPasswordIncludeUppercase = UserValidator.isPasswordIncludeUppercase(password)
        var isPasswordIncludeLetters = UserValidator.isPasswordIncludeLetters(password)
        if (password.isEmpty()){
            return "Masukan password!"
        }
        if (!isPasswordValid){
            if (!isPasswordLengthValid){
                return "Password panjangnya min. 8 karakter"
            }
            if (!isPasswordIncludeUppercase){
                return "Password harus mengandung min. 1 huruf kapital"
            }
            if (!isPasswordWithNum){
                return "Password harus mengandung min. 1 angka"
            }
            if (!isPasswordIncludeLetters){
                return "Password harus mengandung min. 1 huruf kecil"
            }
        }
        return null
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

    private fun setFormJenisKelamin() {
        val jenisKelamin = resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, jenisKelamin)
        autoCompleteTextView = binding.spGenderAdmin
        autoCompleteTextView.setAdapter(arrayAdapter)
        binding.spGenderAdmin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, positiion: Int, id: Long) {
                genderAdmin = adapterView?.getItemAtPosition(positiion).toString().lowercase().trim()
                Toast.makeText(context,"Gender: $genderAdmin", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun bindingView(): View {
        binding = FragmentRegisterRTBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun btnLoginNavigateToLoginAdmin() {
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerRT_to_loginRT)
        }
    }

}