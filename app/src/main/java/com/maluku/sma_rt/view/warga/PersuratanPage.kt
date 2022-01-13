package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.maluku.sma_rt.databinding.FragmentPersuratanPageBinding
import com.maluku.sma_rt.databinding.FragmentPersuratanWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.model.persuratan.GetPersuratanById
import com.maluku.sma_rt.presenter.WargaPersuratanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaPersuratanInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewPersuratanPage
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewSuratMasuk

private const val TAG = "PERSURATAN PAGE"

class PersuratanPage : Fragment(), WargaPersuratanInterface {

    private lateinit var binding: FragmentPersuratanPageBinding

    private lateinit var rvSuratPage: RecyclerView
    private lateinit var adapterSuratPage: RecyclerViewPersuratanPage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WargaPersuratanPresenter(this).getAllDataSurat(getToken(), "1")
        setRecyclerViewPersuratanPage()
        btnBack()
        btnSuratMasuk()
        fabAddSurat()

    }

    private fun btnBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanPage_to_homeWarga)
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun setRecyclerViewPersuratanPage() {
        rvSuratPage = binding.rvPersuratanpage
        rvSuratPage.setHasFixedSize(true)
        rvSuratPage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterSuratPage = RecyclerViewPersuratanPage(
            arrayListOf()
        )
        rvSuratPage.adapter = adapterSuratPage
    }

    private fun btnSuratMasuk() {
        binding.btnSuratmasuk.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanPage_to_persuratanWargaMasuk)
        }
    }

    private fun fabAddSurat() {
        binding.addSurat.setOnClickListener{
            findNavController().navigate(R.id.action_persuratanPage_to_persuratanWarga)
        }
    }

    private fun bindingView(): View {
            binding = FragmentPersuratanPageBinding.inflate(layoutInflater)
            return binding.root
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
        Handler(Looper.getMainLooper()).post {
            adapterSuratPage.setData(result)
        }
    }

    override fun onGetDataFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetDataByIDSuccess(data: GetPersuratanById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataByIDFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLetterReceivedSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLetterReceivedFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLetterRejectedSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onLetterRejectedFailure(message: String) {
        TODO("Not yet implemented")
    }

}