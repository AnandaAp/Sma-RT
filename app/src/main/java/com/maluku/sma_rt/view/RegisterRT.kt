package com.maluku.sma_rt.view

import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
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
        val genderList = listOf("laki-laki", "perempuan")
        val adapter = ArrayAdapter<String>(context!!,
            R.layout.support_simple_spinner_dropdown_item,genderList)
        var isSpinnerInitial = true
        binding.spGenderAdmin.adapter = adapter
        binding.spGenderAdmin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, positiion: Int, id: Long) {
                if (isSpinnerInitial){
                    isSpinnerInitial = false
                } else {
                    genderAdmin = adapterView?.getItemAtPosition(positiion).toString().trim()
                    Toast.makeText(context,"Gender: $genderAdmin", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context,"You selected ${adapterView?.getItemAtPosition(positiion).toString()}",Toast.LENGTH_SHORT).show()
                }
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
            if (password=="" && confirmPassword=="" && namaAdmin=="" && noHpAdmin=="" && emailAdmin=="" && kodeRT==""){
                validRegister = false
                Toast.makeText(context,"Seluruh field harus terisi",Toast.LENGTH_SHORT).show()
                Log.d("TAG", "nama: $namaAdmin; no hp: $noHpAdmin; email: $emailAdmin; kode: $kodeRT; password: $password; confirm pass: $confirmPassword;")
            } else {
                Log.d("TAG", "nama: $namaAdmin; no hp: $noHpAdmin; email: $emailAdmin; kode: $kodeRT; password: $password; confirm pass: $confirmPassword;")
            }
            if (!binding.checkBoxsk.isChecked){
                validRegister = false
                Toast.makeText(context,"Anda harus setuju dengan syarat dan ketentuan yang berlaku!",Toast.LENGTH_LONG).show()
            }
            if (password != confirmPassword){
                validRegister = false
                Toast.makeText(context,"Password tidak sesuai!",Toast.LENGTH_LONG).show()
            }
            if (validRegister){
                Toast.makeText(context,"Valid boi", Toast.LENGTH_SHORT).show()
                registerAdmin(kodeRT,genderAdmin, noHpAdmin,namaAdmin, emailAdmin, password)
            }
        }
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