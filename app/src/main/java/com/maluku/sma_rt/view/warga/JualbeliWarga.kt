package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentJualbeliWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_NAME_KEY
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.presenter.WargaJualBeliPresenter
import com.maluku.sma_rt.view.viewInterface.WargaJualBeliInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTerlaris
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewToko

private const val TAG = "JUAL BELI WARGA"

class JualbeliWarga : Fragment(), WargaJualBeliInterface {
    private lateinit var binding: FragmentJualbeliWargaBinding

    private lateinit var rvToko: RecyclerView
    private lateinit var rvTerlaris: RecyclerView
    private lateinit var adapterToko: RecyclerViewToko
    private lateinit var adapterTerlaris: RecyclerViewTerlaris

    private var namaWarga: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WargaJualBeliPresenter(this).getAllKeluarga(getToken())
        WargaJualBeliPresenter(this).getAllProduk(getToken())
        setRecyclerViewToko()
        setRecyclerViewProduk()
        setDataWarga()
        navigateToBasket()
        navigateToHistoryOrder()
    }

    private fun setDataWarga() {
        val userSession = UserSession(requireActivity())
        namaWarga = userSession
            .getValueString(SHARED_PREFERENCE_NAME_KEY)
        binding.tvNama.text = "Hi, ${namaWarga}"
    }

    private fun navigateToBasket() {
        binding.troli.setOnClickListener {
            findNavController().navigate(R.id.action_jualbeliWarga_to_pesananUserPage)
        }
    }
    private fun navigateToHistoryOrder() {
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_jualbeliWarga_to_riwayatPesananUser)
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun setRecyclerViewToko() {
        rvToko = binding.rvToko
        rvToko.setHasFixedSize(true)
        rvToko.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        adapterToko = RecyclerViewToko(
            arrayListOf()
        )
        rvToko.adapter = adapterToko
    }

    private fun setRecyclerViewProduk() {
        rvTerlaris = binding.rvTerlaris
        rvTerlaris.setHasFixedSize(true)
        rvTerlaris.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterTerlaris = RecyclerViewTerlaris(
            arrayListOf()
        )
        rvTerlaris.adapter = adapterTerlaris
    }

    private fun bindingView(): View {
        binding = FragmentJualbeliWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onGetAllTokoSuccess(result: List<GetAllKeluargaItem>) {
        adapterToko.setData(result as ArrayList<GetAllKeluargaItem>)
    }

    override fun onGetAllTokoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllProdukSuccess(result: List<GetAllProdukItem>) {
        adapterTerlaris.setData(result as ArrayList<GetAllProdukItem>)
    }

    override fun onGetAllProdukFailure(message: String) {
        TODO("Not yet implemented")
    }

}