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
import com.maluku.sma_rt.databinding.FragmentLaporanListBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.aduan.GetAduanById
import com.maluku.sma_rt.model.aduan.GetAllAduanItem
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaAduanInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanList

private const val TAG = "LIST LAPORAN WARGA"

class LaporanList : Fragment(), WargaAduanInterface {

    private lateinit var binding: FragmentLaporanListBinding
    private lateinit var rvLaporanList: RecyclerView
    private lateinit var adapterLaporanList: RecyclerViewLaporanList

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
        fabAddLaporan()
        setRecyclerViewLaporanList()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun setRecyclerViewLaporanList() {
        rvLaporanList = binding.rvLaporanList
        rvLaporanList.setHasFixedSize(true)
        rvLaporanList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterLaporanList = RecyclerViewLaporanList(
            arrayListOf()
        )
        rvLaporanList.adapter = adapterLaporanList
    }

    private fun fabAddLaporan() {
        binding.addLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_laporanPage_to_laporanWarga)
        }
    }




    private fun bindingView(): View {
        binding = FragmentLaporanListBinding.inflate(layoutInflater)
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
        adapterLaporanList.setData(list as List<GetAllAduanItem>)
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
}