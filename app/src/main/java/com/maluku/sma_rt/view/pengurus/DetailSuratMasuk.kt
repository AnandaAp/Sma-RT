package com.maluku.sma_rt.view.pengurus

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.DetailSuratMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.model.persuratan.GetPersuratanById
import com.maluku.sma_rt.presenter.WargaPersuratanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaPersuratanInterface
import com.maluku.sma_rt.view.warga.LaporanWargaDirections

class DetailSuratMasuk: Fragment(), WargaPersuratanInterface {
    private lateinit var binding: DetailSuratMasukBinding
    private var id = ""
    private var judul = ""
    private var keperluan = ""
    private var status = ""
    private var tanggal = ""
    private var penerima = ""
    private var linkDrive = ""

    val args: DetailSuratMasukArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
        dialogTerimaSurat()
        dialogTolakSurat()
        back()
    }

    private fun bindData() {
        id = args.idSurat
        WargaPersuratanPresenter(this).getDataByID(getToken(),id)
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        return token
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View? {
        binding = DetailSuratMasukBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun back(){
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun dialogTerimaSurat(){
        binding.btnSetuju.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_ngirim_persuratan)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
            val btnBatal = dialog.findViewById<TextView>(R.id.btn_batal)

            btnOk.setOnClickListener {
                dialogKirimSurat()
                dialog.dismiss()
            }
            btnBatal.setOnClickListener {
                dialogSukses()
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun dialogTolakSurat(){
        binding.btnTolak.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_surat_ditolak)
            val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)
            btnSimpan.setOnClickListener {
                WargaPersuratanPresenter(this).tolakSurat(getToken(),id)
                dialog.dismiss()
                findNavController()!!.navigate(R.id.action_detailSuratMasuk_to_suratFragment)
            }
            dialog.show()
        }
    }

    private fun dialogSukses() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (linkDrive == "" || linkDrive.isNullOrEmpty()){
            dialog.setContentView(R.layout.custom_dialog_surat_diterima)
        } else {
            dialog.setContentView(R.layout.custom_dialog_file_terkirim)
        }
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)
        btnSimpan.setOnClickListener {
            WargaPersuratanPresenter(this).terimaSurat(getToken(),id,linkDrive)
            dialog.dismiss()
            findNavController()!!.navigate(R.id.action_detailSuratMasuk_to_suratFragment)
        }
        dialog.show()
    }

    private fun dialogKirimSurat(){
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_kirim_link)
        val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)
        val etLink = dialog.findViewById<TextView>(R.id.etLinkDrive)
        btnSimpan.setOnClickListener {
            linkDrive = etLink.text.toString()
            dialog.dismiss()
            dialogSukses()
        }
        dialog.show()
    }

    override fun onCreateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(result: List<GetAllPersuratanItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDSuccess(data: GetPersuratanById?) {
        Handler(Looper.getMainLooper()).post {
            judul = data?.judul.toString()
            keperluan = data?.keperluan.toString()
            penerima = data?.penerima.toString()
            tanggal = data?.tanggal.toString()
            status = "Diajukan"
            setDetailSuratMasuk()
        }
    }

    override fun onGetDataByIDFailure(message: String) {
        if (context!=null){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onLetterReceivedSuccess(message: String) {
        if (context!=null){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onLetterReceivedFailure(message: String) {
        if (context!=null){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onLetterRejectedSuccess(message: String) {
        if (context!=null){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onLetterRejectedFailure(message: String) {
        if (context!=null){
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setDetailSuratMasuk(){
        binding.tvJudul.text = judul
        binding.tvKeperluan.text = keperluan
        binding.tvPenerima.text = penerima
        binding.tvTanggal.text = tanggal
        binding.tvStatus.text = status
    }
}