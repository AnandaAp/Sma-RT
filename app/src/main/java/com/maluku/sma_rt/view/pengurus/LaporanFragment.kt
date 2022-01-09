package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentLaporanBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.LaporanAdapter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface

class LaporanFragment : Fragment(), WargaAduanInterface {
    private lateinit var binding: FragmentLaporanBinding
    private lateinit var rvLaporan: RecyclerView
    private lateinit var adapterLaporan: LaporanAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Refresh Data Laporan
        onStart()
        setRecyclerViewLaporan()
        back()
    }

    override fun onStart() {
        super.onStart()
        WargaAduanPresenter(this).getAllDataAduan(getToken())
    }

    private fun back(){
        binding.btnBack.setOnClickListener {
            findNavController()!!.popBackStack()
        }
    }


    private fun setRecyclerViewLaporan() {
        if (context!=null){
            rvLaporan = binding.rvListLaporan
            rvLaporan.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL ,false)
            adapterLaporan = LaporanAdapter(arrayListOf())
            rvLaporan.adapter = adapterLaporan
        }
    }

    private fun bindingView(): View {
        binding = FragmentLaporanBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
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
        TODO("Not yet implemented")
    }

    override fun onDeleteFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataSuccess(list: List<GetAllAduanItem?>?) {
        adapterLaporan.setData(list as ArrayList<GetAllAduanItem>)
    }

    override fun onGetAllDataFailed(message: String) {
        if (context!=null){
            Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetDataByIDSuccess(list: GetAduanById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDFailed(message: String) {
        TODO("Not yet implemented")
    }

}