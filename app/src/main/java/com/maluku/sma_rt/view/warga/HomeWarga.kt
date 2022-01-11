package com.maluku.sma_rt.view.warga


import android.content.ContentValues
import android.content.Intent
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
import com.maluku.sma_rt.databinding.FragmentHomeWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_EMAIL_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_FAMILY_ID_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_GENDER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_ID_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_NAME_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PHONE_NUMBER_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_PICTURE_KEY
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_FCM
import com.maluku.sma_rt.model.dompetkeluarga.GetAllDompetKeluargaItem
import com.maluku.sma_rt.model.dompetkeluarga.GetDompetKeluargaById
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.presenter.*
import com.maluku.sma_rt.view.activity.MainActivity
import com.maluku.sma_rt.view.viewInterface.*
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfoTerkini
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewKegiatanWarga
import java.io.File
import java.text.NumberFormat
import java.util.*

private const val TAG = "HOME WARGA"

class HomeWarga : Fragment(), InformasiInterface, DompetKeluargaInterface, WargaInterface {
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
    ): View {
        WargaPresenter(requireActivity(), this).getDataLogin(getToken())
        InformasiPresenter(this).getAllInfoTerkini(getToken())
        InformasiPresenter(this).getAllKegiatan(getToken())
        DompetKeluargaPresenter(this).getDompetKeluargaByLoginSession(getToken())
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewInfoTerkini()
        setRecyclerViewKegiatanWarga()
        navigateToMenuInformasi()
        navigateToMenuLaporan()
        navigateToMenuPersuratan()
        navigateToMenuMore()
        navigateToTopUpSaldo()
    }

    private fun setDompetKeluarga(list: GetDompetKeluargaById) {
        saldo = toRupiah(list.jumlah.toString().toDouble())
        binding.angkasaldo.text = saldo
    }

    private fun setDataWarga(data: GetMe) {
        val userSession = UserSession(requireActivity())
        userSession.save(SHARED_PREFERENCE_NAME_KEY, data.nama!!)
        userSession.save(SHARED_PREFERENCE_EMAIL_KEY, data.email!!)
        userSession.save(SHARED_PREFERENCE_GENDER_KEY, data.gender!!)
        userSession.save(SHARED_PREFERENCE_ID_KEY, data.id!!)
        userSession.save(SHARED_PREFERENCE_FAMILY_ID_KEY, data.idKeluarga!!)
        userSession.save(SHARED_PREFERENCE_PICTURE_KEY, data.gambar!!)
        userSession.save(SHARED_PREFERENCE_PHONE_NUMBER_KEY, data.noHp!!)
        userSession.save(SHARED_PREFERENCE_PASSWORD_KEY, data.password!!)
        userSession.save(SHARED_PREFERENCE_TOKEN_FCM,data.tokenFirebase!!)

        namaWarga = data.nama.toString()

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
            Log.i(TAG, "error on get Data from Firebase Storage" +
                    "\nmessage :${it.message}")
        }

    }

    private fun navigateToTopUpSaldo() {
        binding.btnIsisaldo.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_topupSaldo)
        }
    }

    private fun navigateToMenuInformasi() {
        binding.btnInfo.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_informasiWarga)
        }
    }

    private fun navigateToMenuLaporan() {
        binding.btnLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_laporanPage)
        }
    }

    private fun navigateToMenuPersuratan() {
        binding.btnPersuratan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_persuratanPage)
        }
    }

    private fun navigateToMenuMore() {
        binding.btnMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_isisaldoTariksaldo)
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

    override fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>) {
        adapterInfo.setData(result)
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
        adapterKegiatan.setData(result)
    }

    override fun onGetKegiatanFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()

    }

    override fun onUpdateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiFailure(message: String) {
        TODO("Not yet implemented")
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

    override fun onGetAllDataSuccess(result: List<GetAllDompetKeluargaItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(result: GetDompetKeluargaById?) {
        setDompetKeluarga(result!!)
    }

    override fun onLoginSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLoginFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRegisterSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRegisterFailure(message: String) {
        TODO("Not yet implemented")
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
        setDataWarga(result!!)
    }

    override fun onGetDataFailure(message: String) {
//        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        val preferences = UserSession(requireActivity())
        preferences.clearSharedPreference()
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
    }

}