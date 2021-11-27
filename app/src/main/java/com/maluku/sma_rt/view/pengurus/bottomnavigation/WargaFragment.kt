package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentWargaBinding
import com.maluku.sma_rt.extentions.AdminSession
import com.maluku.sma_rt.extentions.AdminSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.model.warga.GetAllWargaItem
import com.maluku.sma_rt.presenter.DaftarWargaPresenter
import com.maluku.sma_rt.view.pengurus.adapter.WargaAdapter
import com.maluku.sma_rt.view.viewInterface.DaftarWargaViewInterface

private const val TAG = "TOKEN LOGIN"

class WargaFragment : Fragment(), DaftarWargaViewInterface {
    private lateinit var binding: FragmentWargaBinding
    private lateinit var token: String
    private lateinit var rvWarga: RecyclerView
    private lateinit var adapterWarga: WargaAdapter

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
        getToken()
        DaftarWargaPresenter(requireActivity(), this).getDaftarWargaPresenter(token)
    }

    private fun bindingView(): View {
        binding = FragmentWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun adapterStart() {
        rvWarga = binding.rvListWarga
        adapterWarga = WargaAdapter(arrayListOf())
        rvWarga.layoutManager = LinearLayoutManager(requireActivity())
        rvWarga.adapter = adapterWarga
    }

    override fun showDataWarga(warga: List<GetAllWargaItem>) {
        updateDataWarga(warga)
    }

    override fun updateDataWarga(warga: List<GetAllWargaItem>) {
        adapterWarga.setData(warga as ArrayList<GetAllWargaItem>)
    }

    override fun resultFailed(t: Throwable) {
        Toast.makeText(requireContext(),"Pesan: $t",Toast.LENGTH_LONG).show()
    }

    private fun getToken(){
        val preferences = AdminSession(requireActivity())
        token = preferences.getValueString(SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
    }

    override fun resultSuccess(warga: List<GetAllWargaItem>) {
        adapterStart()
        showDataWarga(warga)
    }

}