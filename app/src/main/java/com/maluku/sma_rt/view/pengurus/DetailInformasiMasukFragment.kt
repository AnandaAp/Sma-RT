package com.maluku.sma_rt.view.pengurus

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailInformasiMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import java.io.File

class DetailInformasiMasukFragment : Fragment(), InformasiInterface {
    private lateinit var binding: FragmentDetailInformasiMasukBinding
    private var idInformasi: String = ""
    private var judulInformasi: String? = ""
    private var kategoriInformasi: String? = ""
    private var lokasiInformasi: String? = ""
    private var detailInformasi: String? = ""
    private var gambarInformasi: String? = ""
    private var tanggalInformasi: String? = ""

    val args: DetailInformasiMasukFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        navigateDetailToEditInformasi()
        dialogDeleteInformasi()
    }

    private fun bindData() {
        idInformasi = args.idInformasi
        InformasiPresenter(this).getInformasiById(getToken(),idInformasi)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = FragmentDetailInformasiMasukBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        return token
    }

    private fun splitDate(date: String): String {
        val delim = "T"
        val tanggal = date.split(delim)
        return tanggal[0]
    }

    fun tglIndonesia(tgl: String): String {
        val tgl = tgl.split("-")
        val bulan = when (tgl[1]) {
            "01" -> "Januari"
            "02" -> "Februari"
            "03" -> "Maret"
            "04" -> "April"
            "05" -> "Mei"
            "06" -> "Juni"
            "07" -> "Juli"
            "08" -> "Agustus"
            "09" -> "September"
            "10" -> "Oktober"
            "11" -> "November"
            "12" -> "Desember"
            else -> "Terjadi kesalahan"
        }
        return "${tgl[2]} $bulan ${tgl[0]}"
    }

    private fun navigateDetailToEditInformasi(){
        binding.btnToEditInformasi.setOnClickListener {
            val direction = DetailInformasiMasukFragmentDirections.actionDetailInformasiMasukFragmentToEditInformasiFragment(
                judulInformasi!!,kategoriInformasi!!,lokasiInformasi!!,detailInformasi!!,gambarInformasi!!, idInformasi
            )
            view!!.findNavController()!!.navigate(direction)
        }
    }

    private fun dialogDeleteInformasi(){
        binding.btnHapusInformasi.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_ngirim_persuratan)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
            val btnBatal = dialog.findViewById<TextView>(R.id.btn_batal)

            btnOk.setOnClickListener {
                InformasiPresenter(this).deleteInformasi(getToken(), idInformasi)
                dialog.dismiss()
            }
            btnBatal.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
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
        judulInformasi = result?.judul
        lokasiInformasi = result?.lokasi
        tanggalInformasi = result?.createdAt.toString()
        detailInformasi = result?.detail
        gambarInformasi = result?.gambar
        kategoriInformasi = result?.kategori
        binding.tvJudulInformasi.text = judulInformasi
        binding.tvLokasiInformasi.text = lokasiInformasi
        binding.tvTanggalInformasi.text = tglIndonesia(splitDate(tanggalInformasi.toString()))
        binding.tvDetailInformasi.text = detailInformasi
        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("images/${gambarInformasi}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            if (activity != null){
                // Tampilkan gambar dengan Glide
                Glide.with(this)
                    .load(localFile.path)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                    .into(binding.ivInformasi)
            }
        }.addOnFailureListener {

        }
    }

    override fun onGetInformasiFailure(message: String) {
        if (context != null){
            Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiSuccess(message: String) {
        if (context != null){
            Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_SHORT).show()
            val direction = DetailInformasiMasukFragmentDirections.actionDetailInformasiMasukFragmentToInformasiFragment(1)
            view!!.findNavController()!!.navigate(direction)
        }
    }

    override fun onDeleteInformasiFailure(message: String) {
        if (context != null){
            Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_SHORT).show()
        }
    }

}