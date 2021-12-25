package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentInformasiMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.presenter.ListInfoTerkiniPresenter
import com.maluku.sma_rt.presenter.ListKegiatanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.WargaAdapter
import com.maluku.sma_rt.view.viewInterface.ListInfoTerkiniInterface
import com.maluku.sma_rt.view.viewInterface.ListKegiatanInterface

class InformasiMasukFragment : Fragment() {
    private lateinit var binding: FragmentInformasiMasukBinding
    private lateinit var rvInformasi: RecyclerView
    private lateinit var adapterInformasi: WargaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Refresh Data Warga
        onStart()
        setRecyclerViewInformasi()
    }

    private fun setRecyclerViewInformasi() {
        /*
        rvInformasi = binding.rvListInformasi
        rvInformasi.layoutManager = LinearLayoutManager(requireContext())
        adapterInformasi = WargaAdapter(arrayListOf())
        rvInformasi.adapter = adapterInformasi
         */
    }

    override fun onStart() {
        super.onStart()
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

}