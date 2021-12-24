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
import com.maluku.sma_rt.databinding.FragmentProdukPageBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.presenter.WargaTokoListProdukKeluargaPresenter
import com.maluku.sma_rt.view.viewInterface.ListProdukInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage

private const val TAG = "TOKEN LOGIN"

class ProdukPage : Fragment(), ListProdukInterface {

    private lateinit var binding: FragmentProdukPageBinding

    private lateinit var rvProduk: RecyclerView
    private lateinit var adapterProduk: RecyclerViewProdukpage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewListProduk()
        goBack()
        navigateToTambahProduk()
        WargaTokoListProdukKeluargaPresenter(requireActivity(), this).getListProdukByToken(getToken())
    }

    override fun onStart() {
        super.onStart()
        WargaTokoListProdukKeluargaPresenter(requireActivity(), this).getListProdukByToken(getToken())
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

    override fun showDataProduk(produk: List<GetAllProdukKeluargaItem>) {
        updateDataProduk(produk)
    }

    override fun updateDataProduk(produk: List<GetAllProdukKeluargaItem>) {
        adapterProduk.setData(produk as ArrayList<GetAllProdukKeluargaItem>)
    }

    override fun resultSuccess(result: List<GetAllProdukKeluargaItem>) {
        showDataProduk(result)
    }

    override fun resultFailed(t: Throwable) {
        Toast.makeText(requireContext(),"Pesan: $t", Toast.LENGTH_LONG).show()
    }

    override fun onDeleteSuccess(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        WargaTokoListProdukKeluargaPresenter(requireActivity(), this).getListProdukByToken(getToken())
    }

    override fun onDeleteFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

}