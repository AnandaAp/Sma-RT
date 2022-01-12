package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailLaporanSayaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val TAG = "DETAIL LAPORAN SAYA"

class DetailLaporanSaya : Fragment(), WargaAduanInterface {
    val args: DetailLaporanSayaArgs by navArgs()

    private lateinit var binding: FragmentDetailLaporanSayaBinding

    private var idAduan: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        goBack()
        btnDelete()
    }

    private fun bindData() {
        idAduan = args.aduanId
        WargaAduanPresenter(this).getDataAduanByID(getToken(),idAduan)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setData(result: GetAduanById) {
        binding.namaPelapor.text = result.judul.toString()
        binding.textView3.text = result.deskripsi.toString()
        binding.jamLaporan.text = formatTanggal(result.createdAt.toString())

        //Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("aduan/${result.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image   : $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.imageLaporan)
        }.addOnFailureListener {

        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatTanggal(tanggal: String): String {
        val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val timestamp = 1565209665.toLong() // timestamp in Long
        val timestampAsDateString = java.time.format.DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(timestamp))
        val date = LocalDate.parse(timestampAsDateString, secondApiFormat)

        return date.dayOfMonth.toString()+" "+date.month.toString()+" "+date.year.toString()
    }

    private fun goBack(){
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailLaporanSaya_to_laporanPage)
        }
    }

    private fun deleteLaporan() {
        WargaAduanPresenter(this).deleteAduanByID(getToken(),idAduan)
    }

    private fun btnDelete() {
        binding.btnDelete.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_delete_laporan)
            val btnYa = dialog.findViewById<TextView>(R.id.btn_ya)
            val btnTidak = dialog.findViewById<TextView>(R.id.btn_tidak)


            btnYa.setOnClickListener {
                deleteLaporan()
                dialog.dismiss()
            }
            btnTidak.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()
        }
    }



    private fun bindingView(): View {
        binding = FragmentDetailLaporanSayaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_SHORT)
        }
    }

    override fun onDeleteFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_SHORT)
        }
    }

    override fun onGetAllDataSuccess(list: List<GetAllAduanItem?>?) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataFailed(message: String) {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onGetDataByIDSuccess(list: GetAduanById?) {
        Handler(Looper.getMainLooper()).post {
            setData(list!!)
        }
    }

    override fun onGetDataByIDFailed(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_SHORT)
        }
    }

    override fun onReceiveComplaintSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onReceiveComplaintFailure(message: String) {
        TODO("Not yet implemented")
    }

}