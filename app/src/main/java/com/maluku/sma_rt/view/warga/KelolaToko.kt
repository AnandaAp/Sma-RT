package com.maluku.sma_rt.view.warga

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentKelolaTokoBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya
import com.maluku.sma_rt.presenter.WargaKelolaTokoPresenter
import com.maluku.sma_rt.view.viewInterface.KelolaTokoInterface
import java.io.File


private const val TAG = "KELOLA TOKO"

class KelolaToko : Fragment(), KelolaTokoInterface {

    private lateinit var binding: FragmentKelolaTokoBinding

    private var idKeluarga: String = ""
    private var namaToko: String = ""
    private var gambar: String = "default_image"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        navigateToPesanan()
        navigateToProduk()
        navigateToSaldo()
        WargaKelolaTokoPresenter(requireActivity(), this).getDataKeluargaSaya(getToken())
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun setDataToko(data: GetKeluargaSaya) {
        idKeluarga = data!!.id.toString()
        namaToko = data!!.namaToko.toString()
        gambar = data!!.gambar.toString()

        binding.tvNamawarung.text = namaToko

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("toko/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.imgWarung)
        }.addOnFailureListener {

        }

        navigateToPengaturan()
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_kelolaToko_to_akunWarga)
        }
    }

    private fun navigateToProduk() {
        binding.cardProduk.setOnClickListener{
            findNavController().navigate(R.id.action_kelolaToko_to_produkPage)
        }
    }

    private fun navigateToPesanan() {
        binding.cardPesanan.setOnClickListener {
            findNavController().navigate(R.id.action_kelolaToko_to_pesananPenjualPage)
        }
    }

    private fun navigateToSaldo() {
        binding.cardSaldo.setOnClickListener {
            findNavController().navigate(R.id.action_kelolaToko_to_isisaldoTariksaldo)
        }
    }

    private fun navigateToPengaturan() {
        binding.cardPengaturan.setOnClickListener {
            val direction = KelolaTokoDirections
                .actionKelolaTokoToPengaturanToko(
                    idKeluarga,namaToko,gambar
                )
            findNavController().navigate(direction)
        }
    }

    private fun bindingView(): View {
        binding = FragmentKelolaTokoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(list: GetKeluargaSaya?) {
        setDataToko(list!!)
    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }


}