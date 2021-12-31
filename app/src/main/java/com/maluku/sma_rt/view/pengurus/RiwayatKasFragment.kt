package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentRiwayatKasBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.presenter.AdminTagihanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.RiwayatKasAdapter
import com.maluku.sma_rt.view.viewInterface.AdminTagihanInterface

class RiwayatKasFragment : Fragment(), AdminTagihanInterface {
    private lateinit var binding: FragmentRiwayatKasBinding
    private lateinit var rvRiwayat: RecyclerView
    private lateinit var adapterRiwayatKas: RiwayatKasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Refresh data riwayat kas
        onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    private fun bindingView(): View {
        binding = FragmentRiwayatKasBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        AdminTagihanPresenter(this).getAllTagihan(getToken())
    }

    private fun setRecyclerViewRiwayatKas() {
        rvRiwayat = binding.rvRiwayatKas
        rvRiwayat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        adapterRiwayatKas = RiwayatKasAdapter(arrayListOf())
        rvRiwayat.adapter = adapterRiwayatKas
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

    override fun onGetDataSuccess(message: String, list: List<GetAllTagihanItem>) {
        setRecyclerViewRiwayatKas()
        for (lunas in list){
            if (lunas.terbayar == true){
                adapterRiwayatKas.setData(list as ArrayList<GetAllTagihanItem>)
            }
        }
    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message", Toast.LENGTH_LONG).show()
    }

}