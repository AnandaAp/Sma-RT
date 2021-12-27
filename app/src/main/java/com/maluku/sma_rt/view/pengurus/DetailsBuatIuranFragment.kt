package com.maluku.sma_rt.view.pengurus

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentBuatIuranBinding
import com.maluku.sma_rt.databinding.FragmentDetailsBuatIuranBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.presenter.AdminTagihanPresenter
import java.io.File

class DetailsBuatIuranFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBuatIuranBinding
    private var id: String = ""
    private var nama: String = ""
    private var jumlah: String = ""
    private var detail: String = ""
    val args: DetailsBuatIuranFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        namaFocusListener()
        jumlahFocusListener()
        detailFocusListener()
        updateTagihan()
    }

    private fun bindData() {
        id = args.tagihanId
        nama = args.namaTagihan
        detail = args.detailTagihan
        jumlah = args.jumlahTagihan

        binding.etEditNamaTagihan.setText(nama)
        binding.etEditJumlahTagihan.setText(jumlah)
        binding.etEditDetailTagihan.setText(detail)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentDetailsBuatIuranBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun updateTagihan(){
        binding.btnUpdateTagihan.setOnClickListener {
            submitForm()
        }
    }

    private fun submitForm() {
        val validNama = !binding.etEditNamaTagihan.text.isNullOrEmpty()
        val validJumlah = !binding.etEditJumlahTagihan.text.isNullOrEmpty()
        val validDetail = !binding.etEditDetailTagihan.text.isNullOrEmpty()
        if (validNama && validDetail && validJumlah){
            Log.d("UPDATE_IURAN","Nama: $nama, Jumlah: $jumlah, Detail: $detail")
        } else {
            if (!validNama){
                binding.etEditNamaTagihan.error = "Masukan nama tagihan!"
            }
            if (!validJumlah){
                binding.etEditJumlahTagihan.error = "Masukan jumlah tagihan!"
            }
            if (!validDetail){
                binding.etEditDetailTagihan.error = "Berikan detail tagihan!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun namaFocusListener() {
        binding.etEditNamaTagihan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etEditNamaTagihan.error = validNama()
            }
        }
    }

    private fun validNama(): String? {
        nama = binding.etEditNamaTagihan.text.toString().trim()
        if (nama.isEmpty()){
            return "Masukan nama tagihan!"
        }
        return null
    }

    private fun jumlahFocusListener() {
        binding.etEditJumlahTagihan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etEditJumlahTagihan.error = validJumlah()
            }
        }
    }

    private fun validJumlah(): String? {
        jumlah = binding.etEditJumlahTagihan.text.toString().trim()
        if (jumlah.isEmpty()){
            return "Masukan jumlah tagihan!"
        }
        return null
    }

    private fun detailFocusListener() {
        binding.etEditDetailTagihan.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.etEditDetailTagihan.error = validDetail()
            }
        }
    }

    private fun validDetail(): String? {
        detail = binding.etEditDetailTagihan.text.toString()
        if (detail.isEmpty()){
            return "Masukan detail tagihan!"
        }
        return null
    }


}