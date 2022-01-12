package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLaporanSayaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.presenter.WargaKelolaTokoPresenter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanSaya
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage

private const val TAG = "LAPORAN SAYA"

class LaporanSaya : Fragment(), WargaAduanInterface {

    private lateinit var binding: FragmentLaporanSayaBinding
    private lateinit var rvLaporanSaya: RecyclerView
    private lateinit var adapterLaporanSaya: RecyclerViewLaporanSaya


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WargaAduanPresenter(this).getAllDataAduan(getToken())
        setRecyclerViewLaporanSaya()
        fabAddLaporan()
    }

    private fun setRecyclerViewLaporanSaya() {
        rvLaporanSaya = binding.rvLaporanSaya
        rvLaporanSaya.setHasFixedSize(true)
        rvLaporanSaya.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterLaporanSaya = RecyclerViewLaporanSaya(
            arrayListOf()
        )
        rvLaporanSaya.adapter = adapterLaporanSaya
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun fabAddLaporan() {
        binding.addLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_laporanPage_to_laporanWarga)
        }
    }


    private fun bindingView(): View {
        binding = FragmentLaporanSayaBinding.inflate(layoutInflater)
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
        TODO("Not yet implemented")
    }

    override fun onDeleteFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllDataSuccess(list: List<GetAllAduanItem?>?) {
        adapterLaporanSaya.setData(list as List<GetAllAduanItem>)
    }

    override fun onGetAllDataFailed(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetDataByIDSuccess(list: GetAduanById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onReceiveComplaintSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onReceiveComplaintFailure(message: String) {
        TODO("Not yet implemented")
    }
}