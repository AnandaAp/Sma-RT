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
import com.maluku.sma_rt.databinding.FragmentSuratMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.model.persuratan.GetPersuratanById
import com.maluku.sma_rt.presenter.WargaAduanPresenter
import com.maluku.sma_rt.presenter.WargaPersuratanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.SuratMasukAdapter
import com.maluku.sma_rt.view.viewInterface.WargaPersuratanInterface


class FragmentSuratMasuk : Fragment(),WargaPersuratanInterface {
    private lateinit var binding: FragmentSuratMasukBinding
    private lateinit var rvSuratMasuk: RecyclerView
    private lateinit var adapterSuratMasuk: SuratMasukAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onStart() {
        super.onStart()
        setRecyclerViewSuratMasuk()
        WargaPersuratanPresenter(this).getAllDataSurat(getToken(),"1")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout()
        onStart()
    }

    private fun setRecyclerViewSuratMasuk() {
        rvSuratMasuk = binding.rvSuratMasuk
        rvSuratMasuk.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        adapterSuratMasuk = SuratMasukAdapter(arrayListOf())
        rvSuratMasuk.adapter = adapterSuratMasuk
    }

    private fun bindingView(): View {
        binding = FragmentSuratMasukBinding.inflate(layoutInflater)
        return binding.root
    }


    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
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
            adapterSuratMasuk.setData(result)
        }
    }

    override fun onGetDataFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
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