package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.os.Handler
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
import com.maluku.sma_rt.databinding.FragmentProdukPageBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.produk.GetProdukById
import com.maluku.sma_rt.presenter.ProdukPresenter
import com.maluku.sma_rt.view.viewInterface.ProdukInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage
import android.os.Looper




private const val TAG = "PRODUK PAGE"

class ProdukPage : Fragment(), ProdukInterface {

    private lateinit var binding: FragmentProdukPageBinding

    private lateinit var rvProduk: RecyclerView
    private lateinit var adapterProduk: RecyclerViewProdukpage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ProdukPresenter(this).getListProdukByToken(getToken())
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewListProduk()
        goBack()
        navigateToTambahProduk()
//        ProdukPresenter(this).getListProdukByToken(getToken())
    }

    override fun onStart() {
        super.onStart()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun setRecyclerViewListProduk() {
        rvProduk = binding.rvProdukpage
        rvProduk.setHasFixedSize(true)
        rvProduk.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterProduk = RecyclerViewProdukpage(
            arrayListOf()
        )
        rvProduk.adapter = adapterProduk
    }

    private fun navigateToTambahProduk() {
        binding.btnTambahproduk.setOnClickListener{
            findNavController().navigate(R.id.action_produkPage_to_tambahProduk)
        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_produkPage_to_kelolaToko)
        }
    }

    private fun bindingView(): View {
        binding = FragmentProdukPageBinding.inflate(layoutInflater)
        return binding.root
    }
//
//    override fun showDataProduk(produk: List<GetAllProdukKeluargaItem>) {
//        updateDataProduk(produk)
//    }
//
//    override fun updateDataProduk(produk: List<GetAllProdukKeluargaItem>) {
//        adapterProduk.setData(produk as ArrayList<GetAllProdukKeluargaItem>)
//    }
//
//    override fun resultSuccess(result: List<GetAllProdukKeluargaItem>) {
//        showDataProduk(result)
//    }
//
//    override fun resultFailed(t: Throwable) {
//        Toast.makeText(requireContext(),"Pesan: $t", Toast.LENGTH_LONG).show()
//    }

    override fun onGetAllDataSuccess(data: List<GetAllProdukKeluargaItem?>?) {
        Handler(Looper.getMainLooper()).post(Runnable { //do stuff like remove view etc
            adapterProduk.setData(data as ArrayList<GetAllProdukKeluargaItem>)
        })
    }

    override fun onGetAllDataFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetDataSuccess(data: GetProdukById?) {
        TODO("Not yet implemented")
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
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

}