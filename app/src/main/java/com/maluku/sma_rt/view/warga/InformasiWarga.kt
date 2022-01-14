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
import com.maluku.sma_rt.databinding.FragmentInformasiWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.presenter.WargaTagihanPresenter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInformasiWarga
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTagihanWarga

private const val TAG = "INFORMASI WARGA"

class InformasiWarga : Fragment(), InformasiInterface {

    private lateinit var binding: FragmentInformasiWargaBinding
    private lateinit var rvInformasi: RecyclerView
    private lateinit var adapterInformasi: RecyclerViewInformasiWarga

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onStart() {
        super.onStart()
        InformasiPresenter(this).getAllInformasi(getToken())
        setRecyclerViewInformasiWarga()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        onStart()
        swipeRefreshLayout()
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_informasiWarga_to_homeWarga)
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun setRecyclerViewInformasiWarga() {
        rvInformasi = binding.rvInformasiWarga
        rvInformasi.setHasFixedSize(true)
        rvInformasi.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterInformasi = RecyclerViewInformasiWarga(
            arrayListOf()
        )
        rvInformasi.adapter = adapterInformasi
    }


    private fun bindingView(): View {
        binding = FragmentInformasiWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateInformasiFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>) {
        adapterInformasi.setData(result)
    }

    override fun onGetAllInformasiFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
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
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanFailure(message: String) {Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onUpdateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onDeleteInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

}