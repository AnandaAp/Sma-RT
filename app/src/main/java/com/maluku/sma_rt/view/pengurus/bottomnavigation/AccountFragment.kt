package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentAccountBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetrt.GetDompetById
import com.maluku.sma_rt.model.pengurus.GetPengurusById
import com.maluku.sma_rt.model.warga.GetAllWargaItem
import com.maluku.sma_rt.presenter.AdminRTProfilePresenter
import com.maluku.sma_rt.presenter.DompetRTPresenter
import com.maluku.sma_rt.presenter.ListWargaPresenter
import com.maluku.sma_rt.view.activity.MainActivity
import com.maluku.sma_rt.view.pengurus.DaftarBuatIuranFragmentDirections
import com.maluku.sma_rt.view.viewInterface.AdminRTProfileInterface
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import com.maluku.sma_rt.view.viewInterface.ListWargaViewInterface
import java.io.File
import java.text.NumberFormat
import java.util.*

class AccountFragment : Fragment(), DompetRTInterface, AdminRTProfileInterface, ListWargaViewInterface {
    private lateinit var binding: FragmentAccountBinding
    private var idPengurus : String = ""
    private var nama : String = ""
    private var noHp : String = ""
    private var gender : String = ""
    private var gambar : String = ""
    private var email : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    private fun bindingView(): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateAkunToEditProfil()
        AdminRTProfilePresenter(this).getDataLoginPengurus(getToken())
        DompetRTPresenter(this).getDompetRTByLogin(getToken())
        ListWargaPresenter(requireActivity(),this).getListWargaPresenter(getToken(),null)
        logout()
    }

    private fun logout(){
        binding.btnLogout.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    override fun onGetAllDataSuccess(result: GetDompetById?) {
        if (result != null) {
            binding.tvTotalKasRT.text = rupiah(result.jumlah.toString().toDouble())
        }
    }

    override fun onGetAllDataFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan $message", Toast.LENGTH_LONG).show()
    }

    override fun onWithdrawSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onWithdrawFailed(message: String) {
        TODO("Not yet implemented")
    }

    private fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(result: GetPengurusById?) {
        nama = result!!.nama.toString()
        gambar = result!!.gambar.toString()
        email = result!!.email.toString()
        noHp = result!!.noHp.toString()
        gender = result!!.gender.toString()
        idPengurus = result!!.id.toString()
        binding.namaPengurus.text = nama
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                .into(binding.ivPengurus)
        }.addOnFailureListener {

        }
    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    private fun navigateAkunToEditProfil() {
        binding.btnEditProfil.setOnClickListener{
            val direction = AccountFragmentDirections.actionNavigationProfilToEditProfileFragment(
                idPengurus,nama,gambar,noHp,gender,email
            )
            view!!.findNavController().navigate(direction)
        }
    }

    override fun resultListWargaSuccess(result: List<GetAllWargaItem>) {
        binding.tvTotalWarga.text = result.size.toString()
    }

    override fun resultListWargaFailed(t: Throwable) {
        Toast.makeText(requireContext(),"Pesan ${t.message.toString()}", Toast.LENGTH_LONG).show()
    }

}