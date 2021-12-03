package com.maluku.sma_rt.view.warga

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentTambahProdukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.presenter.WargaTokoTambahProdukPresenter
import com.maluku.sma_rt.view.viewInterface.TambahProdukInterface

private const val TAG = "TOKEN LOGIN"

class TambahProduk : Fragment(), TambahProdukInterface {

    private lateinit var binding: FragmentTambahProdukBinding

    private var namaProduk: String = ""
    private var detailProduk: String = "aaa"
    private var hargaProduk: String = ""
    private var gambarProduk: String = "default_image"
    private var imageUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        namaFocusListener()
        hargaFocusListener()
        tambahProduk()
        goBack()
    }

    private fun tambahProduk() {
        binding.btnSimpan.setOnClickListener {
            binding.edNamaproduk.clearFocus()
            binding.edHargaproduk.clearFocus()
            validasiTambahProduk()
        }
    }

    private fun validasiTambahProduk(){
        val validNama = !binding.edNamaproduk.text.isNullOrEmpty()
        val validHarga = !binding.edHargaproduk.text.isNullOrEmpty()
//        val validDetail = !binding.etKeteranganProduk.text.isNullOrEmpty()

        if (validNama && validHarga){
//            if (imageUri != null){
//                uploadImage()
//            }
            addProduct(namaProduk,detailProduk,gambarProduk,hargaProduk)
        } else {
            if (!validNama){
                binding.edNamaproduk.error = "Masukan nama produk!"
            }
//            if (!validDetail){
//                binding.etKeteranganProduk.error = "Berikan deskripsi produk!"
//            }
            if (!validHarga){
                binding.edHargaproduk.error = "Masukan harga!"
            }
            Toast.makeText(requireContext(),"Seluruh field harus terisi!", Toast.LENGTH_LONG).show()
        }
    }

    private fun namaFocusListener() {
        binding.edNamaproduk.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edNamaproduk.error = validNamaProduk()
            }
        }
    }

    private fun validNamaProduk(): String? {
        namaProduk = binding.edNamaproduk.text.toString().trim()
        if (namaProduk.isEmpty()){
            return "Masukan nama produk!"
        }
        return null
    }

    private fun hargaFocusListener() {
        binding.edHargaproduk.setOnFocusChangeListener { view, focused ->
            if (!focused){
                binding.edHargaproduk.error = validHargaProduk()
            }
        }
    }

    private fun validHargaProduk(): String? {
        hargaProduk = binding.edHargaproduk.text.toString().trim()
        if (hargaProduk.isEmpty()){
            return "Masukan harga produk!"
        }
        return null
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_tambahProduk_to_produkPage)
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindingView(): View {
        binding = FragmentTambahProdukBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun addProduct(nama: String, detail: String, gambar: String, harga: String) {
        WargaTokoTambahProdukPresenter(requireActivity(),this).tambahProduk(
            getToken(),
            namaProduk,
            detailProduk,
            gambarProduk,
            hargaProduk
        )
    }

    override fun onCreateSuccess(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        val direction = TambahProdukDirections.actionTambahProdukToProdukPage()
        findNavController().navigate(direction)
    }

    override fun onCreateFailed(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }
}