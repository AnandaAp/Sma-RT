package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentLaporanMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.LaporanDiterimaAdapter
import com.maluku.sma_rt.view.pengurus.adapter.LaporanMasukAdapter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface

class LaporanDiterimaFragment : Fragment(), WargaAduanInterface {
    private lateinit var binding: FragmentLaporanMasukBinding
    private lateinit var rvLaporan: RecyclerView
    private lateinit var adapterLaporanDiterima: LaporanDiterimaAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout()
        // Refresh Data Laporan
        onStart()
    }

    override fun onStart() {
        super.onStart()
        setRecyclerViewLaporan()
        WargaAduanPresenter(this).getAllDataAduan(getToken())
    }

    private fun setRecyclerViewLaporan() {
        if (context!=null){
            rvLaporan = binding.rvListLaporan
            rvLaporan.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL ,false)
            adapterLaporanDiterima = LaporanDiterimaAdapter(arrayListOf())
            rvLaporan.adapter = adapterLaporanDiterima
        }
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun bindingView(): View {
        binding = FragmentLaporanMasukBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
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
        Handler(Looper.getMainLooper()).post {
            val listLaporanDiterima: ArrayList<GetAllAduanItem> = arrayListOf()
            for (laporan in list!!){
                if (laporan!!.status.toString() == "Diterima"){
                    listLaporanDiterima.add(laporan!!)
                }
            }
            if (listLaporanDiterima.size >= 1){
                adapterLaporanDiterima.setData(listLaporanDiterima)
            }
        }
    }

    override fun onGetAllDataFailed(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(),"Pesan: $message", Toast.LENGTH_LONG).show()
        }
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