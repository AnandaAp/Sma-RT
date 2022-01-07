package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentSuratKeluarBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.presenter.WargaPersuratanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.SuratKeluarAdapter
import com.maluku.sma_rt.view.viewInterface.WargaPersuratanInterface


class FragmentSuratKeluar : Fragment(), WargaPersuratanInterface {
    private lateinit var binding: FragmentSuratKeluarBinding
    private lateinit var rvSuratKeluar: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewSuratKeluar()
    }

    private fun setRecyclerViewSuratKeluar() {
        val suratKeluarAdapter = SuratKeluarAdapter()
        rvSuratKeluar = binding.rvSuratKeluar
        rvSuratKeluar.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        rvSuratKeluar.adapter = suratKeluarAdapter
    }

    private fun bindingView(): View {
        binding = FragmentSuratKeluarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
//        WargaPersuratanPresenter(this).getAllData(getToken())
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

    override fun onGetDataSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
    }
}