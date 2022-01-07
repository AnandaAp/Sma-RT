package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentGaleriKegiatanRTBinding
import com.maluku.sma_rt.databinding.FragmentInformasiTerkiniRTBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.pengurus.adapter.ListKegiatanAdapter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface

class GaleriKegiatanRTFragment : Fragment(), InformasiInterface {
    private lateinit var binding: FragmentGaleriKegiatanRTBinding
    private lateinit var rvKegiatan: RecyclerView
    private lateinit var adapterKegiatan: ListKegiatanAdapter

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
        // Refresh Data Informasi
        onStart()
        back()
    }

    private fun setRecyclerViewInformasi() {
        if (context!=null){
            rvKegiatan = binding.rvListKegiatan
            rvKegiatan.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL ,false)
            adapterKegiatan = ListKegiatanAdapter(arrayListOf())
            rvKegiatan.adapter = adapterKegiatan
        }
    }

    override fun onStart() {
        super.onStart()
        InformasiPresenter(this).getAllKegiatan(getToken())
    }

    private fun bindingView(): View {
        binding = FragmentGaleriKegiatanRTBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        return token
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
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
        adapterKegiatan.setData(result as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetKegiatanFailure(message: String) {
        if (context!=null){
            Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
        }
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

    private fun back(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}