package com.maluku.sma_rt.view.pengurus

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRegisterRTBinding
import com.maluku.sma_rt.extentions.UserValidator
import com.maluku.sma_rt.presenter.AdminRTRegisterPresenter
import com.maluku.sma_rt.view.activity.DashboardRTActivity
import com.maluku.sma_rt.view.viewInterface.RegisterAdminInterface

private const val TAG = "REGISTER RT"
private var token_firebase = ""

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
        return bindingView()
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
        getFCMToken()
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
        val validCheckBox = binding.checkBoxsk.isChecked
        if (validName && validEmail && validPassword && validConfirmPassword && validKodeRT && validNoHp && validCheckBox){
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
            if (!validCheckBox){
                Toast.makeText(context,"Anda harus setuju dengan syarat dan ketentuan yang berlaku!",Toast.LENGTH_LONG).show()
            }
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
        AdminRTRegisterPresenter(requireActivity(), this).registerNewAdmin(
            inputKodeRT,inputGenderAdmin,inputNoHpAdmin,inputNamaAdmin,inputEmailAdmin,inputPassword,
            token_firebase
        )
    }

    override fun onRegisterSuccess(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
        navigateToDashboard()
    }

    private fun setFormJenisKelamin() {
        val jenisKelamin =  resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, jenisKelamin)
        autoCompleteTextView = binding.spGenderAdmin
        autoCompleteTextView.setAdapter(arrayAdapter)
        binding.spGenderAdmin.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            genderAdmin = parent.getItemAtPosition(position).toString().lowercase().trim()
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

    private fun navigateToDashboard() {
        Thread.sleep(1000)
        val intent = Intent(
            requireActivity(),
            DashboardRTActivity::class.java
        )
        startActivity(intent)
        requireActivity().finish()
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
            Log.d(TAG, "Token Pengurus saat ini: $token_firebase")
            Log.d(ContentValues.TAG, "Token saat ini: $token")
        })
    }

}