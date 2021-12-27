package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentInformasiMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.pengurus.adapter.InformasiMasukAdapter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface

class InformasiMasukFragment : Fragment(), InformasiInterface{
    private lateinit var binding: FragmentInformasiMasukBinding
    private lateinit var rvInformasi: RecyclerView
    private lateinit var adapterInformasi: InformasiMasukAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Refresh Data Informasi
        onStart()
        setRecyclerViewInformasi()
    }

    private fun setRecyclerViewInformasi() {
        rvInformasi = binding.rvInformasiMasuk
        rvInformasi.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL ,false)
        adapterInformasi = InformasiMasukAdapter(arrayListOf())
        rvInformasi.adapter = adapterInformasi
    }

    override fun onStart() {
        super.onStart()
        InformasiPresenter(this).getAllInformasi(getToken())
    }

    private fun bindingView(): View {
        binding = FragmentInformasiMasukBinding.inflate(layoutInflater)
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

    override fun onCreateInformasiFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>) {
        adapterInformasi.setData(result as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetAllInformasiFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    override fun onGetInformasiSuccess(result: List<GetInformasiById>) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun showDataInfoTerkini(info: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun updateDataInfoTerkini(info: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun showDataKegiatan(kegiatan: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun updateDataKegiatan(kegiatan: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

}