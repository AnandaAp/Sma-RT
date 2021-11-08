package com.maluku.sma_rt

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.maluku.sma_rt.databinding.FragmentRegisterRTBinding

class RegisterRT : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentRegisterRTBinding
    private lateinit var inputIdRT: String
    private lateinit var inputNamaAdmin: String
    private lateinit var inputAlamatAdmin: String
    private lateinit var inputGenderAdmin: String
    private lateinit var inputNoHpAdmin: String
    private lateinit var inputConfirmPassword: String
    private lateinit var inputPassword: String
    private lateinit var inputKodePos: String
    private lateinit var inputEmailAdmin: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentRegisterRTBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val genderList = listOf("Laki-Laki", "Perempuan")
        val adapter = ArrayAdapter<String>(context!!, R.layout.support_simple_spinner_dropdown_item,genderList)
        var isSpinnerInitial = true
        binding.spGenderAdmin.adapter = adapter
        inputIdRT = binding.inputIdRT.toString()
        inputNamaAdmin = binding.inputNamaAdmin.toString()
        inputAlamatAdmin = binding.inputAlamatAdmin.toString()
        inputNoHpAdmin = binding.inputNoHpAdmin.toString()
        inputPassword = binding.inputPassword.toString()
        inputConfirmPassword = binding.inputConfirmPassword.toString()
        inputKodePos = binding.inputKodePos.toString()
        inputEmailAdmin = binding.inputEmailAdmin.toString()
        binding.spGenderAdmin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, positiion: Int, id: Long) {
                if (isSpinnerInitial){
                    isSpinnerInitial = false
                } else {
                    Toast.makeText(context,"You selected ${adapterView?.getItemAtPosition(positiion).toString()}",Toast.LENGTH_LONG).show()
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

}