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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WargaJualBeliPresenter(requireActivity(), this).getAllKeluarga(getToken())
        WargaJualBeliPresenter(requireActivity(), this).getAllProduk(getToken())
        setRecyclerViewToko()
        setRecyclerViewProduk()
        navigateToBasket()
    }

    private fun navigateToBasket() {
        binding.troli.setOnClickListener {
            findNavController().navigate(R.id.action_jualbeliWarga_to_pesananUserPage)
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

    override fun showDataToko(toko: List<GetAllKeluargaItem>) {
        updateDataToko(toko)
    }

    override fun updateDataToko(toko: List<GetAllKeluargaItem>) {
        adapterToko.setData(toko as ArrayList<GetAllKeluargaItem>)
    }

    override fun showDataProduk(produk: List<GetAllProdukItem>) {
        updateDataProduk(produk)
    }

    override fun updateDataProduk(produk: List<GetAllProdukItem>) {
        adapterTerlaris.setData(produk as ArrayList<GetAllProdukItem>)
    }

}