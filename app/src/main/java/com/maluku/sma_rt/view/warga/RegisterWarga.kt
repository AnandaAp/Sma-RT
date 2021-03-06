package com.maluku.sma_rt.view.warga

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRegisterWargaBinding
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.WargaPresenter
import com.maluku.sma_rt.view.activity.DashboardWargaActivity
import com.maluku.sma_rt.view.viewInterface.WargaInterface


class RegisterWarga : Fragment(), WargaInterface {

    private lateinit var binding: FragmentRegisterWargaBinding
    private lateinit var autoCompleteTextView: AutoCompleteTextView

    // variabel data
    private var kode_keluarga: String = ""
    private var gender: String = ""
    private var no_hp: String = ""
    private var nama: String = ""
    private var email: String = ""
    private var password : String = ""
    private var password2: String = ""
    private var token_firebase: String = ""
    private var validRegister: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFormJenisKelamin()
        btnLoginNavigateToLoginWarga()
        btnDaftarOnRegister()
        getFCMToken()
    }

    private fun btnDaftarOnRegister() {
        binding.btnDaftar.setOnClickListener {
            setRegisterData()
            validasiData()
            if(validRegister) {
                registerWarga(
                    kode_keluarga,
                    gender,
                    no_hp,
                    nama,
                    email,
                    password,
                    token_firebase
                )
            }
        }
    }

    private fun validasiData() {
        validRegister = true
        if(kode_keluarga.isEmpty()) {
            binding.etIdKeluarga.error = "Masukkan id keluarga!"
            validRegister = false
        }
//        if(gender.isEmpty()) {
//            binding.etJenisKelamin.error = "Pilih jenis kelamin!"
//            validRegister = false
//        }
        if(no_hp.isEmpty()) {
            binding.etNoHP.error = "Masukkan nomor handphone!"
            validRegister = false
        }
        if(nama.isEmpty()) {
            binding.etNamaLengkap.error = "Masukkan nama lengkap!"
            validRegister = false
        }
        if(email.isEmpty()) {
            binding.etEmail.error = "Masukkan email!"
            validRegister = false
        }
        if(password.isEmpty()) {
            binding.etPassword.error = "Masukkan password!"
            validRegister = false
        }
        if(password2.isEmpty()) {
            binding.etPasswordConfirm.error = "Masukkan konfirmasi password!"
            validRegister = false
        }
        if (!binding.checkboxSyarat.isChecked){
            validRegister = false
            Toast.makeText(context,"Anda harus setuju dengan syarat dan ketentuan yang berlaku!",Toast.LENGTH_LONG).show()
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            validRegister = false
            binding.etEmail.error = "Email tidak valid!"
        }
        if(!konfirmasiPassword(password, password2)) {
            validRegister = false
            binding.etPasswordConfirm.error = "Password dan konfirmasi password tidak sama!"
        }
        if(!isValidPassword(password)) {
            validRegister = false
            binding.etPassword.error = "Password panjangnya min. 8 karakter, serta mengandung min. 1 huruf besar, 1 huruf kecil, dan 1 angka!"
        }
    }

    private fun setRegisterData() {
        kode_keluarga = binding.etIdKeluarga.text!!.trim().toString()
        gender = binding.etJenisKelamin.text!!.trim().toString().lowercase()
        no_hp = binding.etNoHP.text!!.trim().toString()
        nama = binding.etNamaLengkap.text!!.trim().toString()
        email = binding.etEmail.text!!.trim().toString()
        password = binding.etPassword.text!!.trim().toString()
        password2 = binding.etPasswordConfirm.text!!.trim().toString()
    }

    private fun konfirmasiPassword(p1: String, p2: String): Boolean {
        if(p1 != p2) return false
        else return true
    }

    fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
            val passwordMatcher = Regex(passwordPattern)
            return passwordMatcher.find(password) != null
        } ?: return false
    }



    private fun btnLoginNavigateToLoginWarga() {
        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerWarga_to_loginWarga)
        }
    }

    private fun setFormJenisKelamin() {
        val jenisKelamin = resources.getStringArray(R.array.jenis_kelamin)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, jenisKelamin)

        autoCompleteTextView = binding.etJenisKelamin
        autoCompleteTextView.setAdapter(arrayAdapter)
        binding.etJenisKelamin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, positiion: Int, id: Long) {
                gender = adapterView?.getItemAtPosition(positiion).toString().lowercase().trim()
                Toast.makeText(context,"Gender: $gender", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun bindingView(): View {
        binding = FragmentRegisterWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    fun registerWarga(
        kode_keluarga: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        password: String,
        token_firebase: String
    ) {
        WargaPresenter(requireActivity(), this)
            .create(
                kode_keluarga,
                gender,
                no_hp,
                nama,
                email,
                password,
                token_firebase
            )
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
        TODO("Not yet implemented")
    }

    override fun onLoginFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRegisterSuccess(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
//        navigateToDashboard()
        val direction = RegisterWargaDirections
            .actionRegisterWargaToViewPagerFragment()
        findNavController().navigate(direction)
    }

    override fun onRegisterFailure(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show()
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
            Log.d("WARGA", "Token Register Warga saat ini: $token_firebase")
            Log.d(ContentValues.TAG, "Token saat ini: $token")
        })
    }

}