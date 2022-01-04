package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentHomeBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetrt.GetDompetById
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.model.pengurus.GetPengurusById
import com.maluku.sma_rt.presenter.AdminRTProfilePresenter
import com.maluku.sma_rt.presenter.DompetRTPresenter
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.pengurus.adapter.GaleriAdapter
import com.maluku.sma_rt.view.pengurus.adapter.InfoAdapter
import com.maluku.sma_rt.view.viewInterface.AdminRTProfileInterface
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), InformasiInterface, DompetRTInterface, AdminRTProfileInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvGaleri: RecyclerView
    private lateinit var adapterGaleri: GaleriAdapter
    private lateinit var rvInfo: RecyclerView
    private lateinit var adapterInfo: InfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewInformasi()
        navigateDashboardToTambahKelurga()
        navigateDashboardToSurat()
        navigateDashboardToInformasi()
        navigateDashboardToLaporan()
        navigateDashboardToRiwayat()
        navigateInfoTerkiniToDetailInformasi()
        navigateGaleriToDetailInformasi()
    }

    override fun onStart() {
        super.onStart()
        InformasiPresenter(this).getAllInfoTerkini(getToken())
        InformasiPresenter(this).getAllKegiatan(getToken())
        DompetRTPresenter(this).getDompetRTByLogin(getToken())
        AdminRTProfilePresenter(this).getDataLoginPengurus(getToken())
    }

    private fun setRecyclerViewInformasi(){
        rvGaleri = binding.rvGaleriWarga
        adapterGaleri = GaleriAdapter(arrayListOf())
        rvInfo = binding.rvInfoTerkini
        adapterInfo = InfoAdapter(arrayListOf())
        rvGaleri.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvGaleri.adapter = adapterGaleri
        rvInfo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvInfo.adapter = adapterInfo
    }

    private fun navigateDashboardToTambahKelurga() {
        binding.btnTambahKeluarga.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_tambahKeluargaFragment)
        }
    }

    private fun navigateDashboardToRiwayat() {
        binding.btnToRiwayat.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_riwayatKasFragment)
        }
    }

    private fun navigateDashboardToSurat() {
        binding.btnSurat.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_suratFragment)
        }
    }

    private fun navigateDashboardToInformasi() {
        binding.btnInformasi.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_informasiFragment)
        }
    }

    private fun navigateDashboardToLaporan() {
        binding.btnLaporan.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_laporanFragment)
        }
    }

    private fun bindingView(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    override fun onCreateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetAllInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiSuccess(result: GetInformasiById?) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }


    override fun onGetInfoTerkiniSuccess(data: List<GetAllInformasiItem>) {
        adapterInfo.setData(data as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }


    override fun onGetKegiatanSuccess(data: List<GetAllInformasiItem>) {
        adapterGaleri.setData(data as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetKegiatanFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    override fun onGetAllDataSuccess(result: GetDompetById?) {
        if (result != null) {
            binding.tvTotalKasRT.text = rupiah(result.jumlah.toString().toDouble())
        }
    }

    override fun onGetAllDataFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    override fun onWithdrawSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onWithdrawFailure(message: String) {
        TODO("Not yet implemented")
    }

    private fun rupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(result: GetPengurusById?) {
        binding.tvNamaPengurus.text = "Hi, ${result?.nama.toString()}"
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${result?.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(10)))
                .into(binding.ivProfilPengurus)
        }.addOnFailureListener {

        }
    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    private fun navigateInfoTerkiniToDetailInformasi() {
        binding.btnSeeAll1.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_informasiTerkiniRTFragment2)
        }
    }

    private fun navigateGaleriToDetailInformasi() {
        binding.btnSeeAll2.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_galeriKegiatanRTFragment)
        }
    }
}