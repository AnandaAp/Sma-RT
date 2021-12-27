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
import com.maluku.sma_rt.R



import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.databinding.FragmentHomeWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.dompetkeluarga.GetAllDompetKeluargaItem
import com.maluku.sma_rt.model.dompetkeluarga.GetDompetKeluargaById
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.*
import com.maluku.sma_rt.view.viewInterface.DompetKeluargaInterface
import com.maluku.sma_rt.view.viewInterface.ListInfoTerkiniInterface
import com.maluku.sma_rt.view.viewInterface.ListKegiatanInterface
import com.maluku.sma_rt.view.viewInterface.WargaEditProfileInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewKegiatanWarga
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfoTerkini
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "HOME WARGA"

class HomeWarga : Fragment(), ListInfoTerkiniInterface, ListKegiatanInterface, DompetKeluargaInterface, WargaEditProfileInterface {
    private lateinit var binding: FragmentHomeWargaBinding

    private lateinit var rvInfo: RecyclerView
    private lateinit var rvKegiatan: RecyclerView
    private lateinit var adapterInfo: RecyclerViewInfoTerkini
    private lateinit var adapterKegiatan: RecyclerViewKegiatanWarga

    private var saldo: String = ""
    private var namaWarga: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        WargaEditProfilePresenter(this).getDataLogin(getToken())
        ListInfoTerkiniPresenter(requireActivity(), this).getListInfoTerkini(getToken())
        ListKegiatanPresenter(requireActivity(), this).getListKegiatan(getToken())
        DompetKeluargaPresenter(this).getDompetKeluargaByLoginSession(getToken())
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewInfoTerkini()
        setRecyclerViewKegiatanWarga()
        navigateToMenuLaporan()
        navigateToMenuPersuratan()
        navigateToTopUpSaldo()
    }

    private fun setDompetKeluarga(list: GetDompetKeluargaById) {
        saldo = toRupiah(list.jumlah.toString().toDouble())
        binding.angkasaldo.text = saldo
    }

    private fun setDataWarga(data: GetMe) {
        namaWarga = data!!.nama.toString()

        binding.textView4.text = "Hi, ${namaWarga}"

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.profileImage)
        }.addOnFailureListener {

        }

    }

    private fun navigateToTopUpSaldo() {
        binding.btnIsisaldo.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_topupSaldo)
        }
    }

    private fun navigateToMenuLaporan() {
        binding.btnLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_laporanWarga)
        }
    }

    private fun navigateToMenuPersuratan() {
        binding.btnPersuratan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_persuratanWarga)
        }
    }

    private fun setRecyclerViewInfoTerkini() {
        rvInfo = binding.rvInfo
        rvInfo.setHasFixedSize(true)
        rvInfo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        adapterInfo = RecyclerViewInfoTerkini(
            arrayListOf()
        )
        rvInfo.adapter = adapterInfo
    }

    private fun setRecyclerViewKegiatanWarga() {
        rvKegiatan = binding.rvKegiatan
        rvKegiatan.setHasFixedSize(true)
        rvKegiatan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        adapterKegiatan = RecyclerViewKegiatanWarga(
            arrayListOf()
        )
        rvKegiatan.adapter = adapterKegiatan
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindingView(): View {
        binding = FragmentHomeWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun showDataInfoTerkini(info: List<GetAllInformasiItem>) {
        updateDataInfoTerkini(info)
    }

    override fun updateDataInfoTerkini(info: List<GetAllInformasiItem>) {
        adapterInfo.setData(info as ArrayList<GetAllInformasiItem>)
    }

    override fun showDataKegiatan(kegiatan: List<GetAllInformasiItem>) {
        updateDataKegiatan(kegiatan)
    }

    override fun updateDataKegiatan(kegiatan: List<GetAllInformasiItem>) {
        adapterKegiatan.setData((kegiatan as ArrayList<GetAllInformasiItem>))
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    override fun onTopupSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onTopupFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onWithdrawSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onWithdrawFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataSuccess(list: List<GetAllDompetKeluargaItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(list: GetDompetKeluargaById?) {
        setDompetKeluarga(list!!)
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(list: GetMe?) {
        setDataWarga(list!!)
    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

}