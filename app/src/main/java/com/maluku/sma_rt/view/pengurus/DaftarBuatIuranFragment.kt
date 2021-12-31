package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentBuatIuranBinding
import com.maluku.sma_rt.databinding.FragmentDaftarBuatIuranBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.presenter.AdminTagihanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.InformasiMasukAdapter
import com.maluku.sma_rt.view.pengurus.adapter.ListTagihanAdapter
import com.maluku.sma_rt.view.pengurus.adapter.RiwayatKasAdapter
import com.maluku.sma_rt.view.viewInterface.AdminTagihanInterface


class DaftarBuatIuranFragment : Fragment(),AdminTagihanInterface {
    private lateinit var binding: FragmentDaftarBuatIuranBinding
    private lateinit var rvListTagihan: RecyclerView
    private lateinit var adapterListTagihan: ListTagihanAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewListTagihan()
        navigateDaftarTagihanToCreateTagihan()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onStart() {
        super.onStart()
        AdminTagihanPresenter(this).getAllTagihan(getToken())
    }


    private fun setRecyclerViewListTagihan() {
        rvListTagihan = binding.rvListTagihan
        rvListTagihan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)
        adapterListTagihan = ListTagihanAdapter(arrayListOf())
        rvListTagihan.adapter = adapterListTagihan
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun bindingView(): View? {
        binding = FragmentDaftarBuatIuranBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun navigateDaftarTagihanToCreateTagihan(){
        binding.btnTambahTagihan.setOnClickListener {
            findNavController().navigate(R.id.action_daftarBuatIuranFragment_to_buatIuranFragment2)
        }
    }

    override fun onCreateSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateFailed(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetDataSuccess(message: String, list: List<GetAllTagihanItem>) {
        for (lunas in list){
            if (lunas.terbayar == false){
                adapterListTagihan.setData(list as ArrayList<GetAllTagihanItem>)
            }
        }

    }

    override fun onGetDataFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message", Toast.LENGTH_LONG).show()
    }

}