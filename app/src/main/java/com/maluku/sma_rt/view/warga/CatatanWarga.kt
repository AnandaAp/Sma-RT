package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentCatatanWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.presenter.WargaTagihanPresenter
import com.maluku.sma_rt.view.viewInterface.WargaTagihanInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewKegiatanWarga
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTagihanWarga
import java.util.ArrayList
import android.R

import androidx.navigation.fragment.NavHostFragment




private const val TAG = "TAGIHAN WARGA"

class CatatanWarga : Fragment(), WargaTagihanInterface {


    private lateinit var binding: FragmentCatatanWargaBinding
    private lateinit var rvTagihan: RecyclerView
    private lateinit var adapterTagihan: RecyclerViewTagihanWarga


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WargaTagihanPresenter(this).getAllTagihan(getToken())
        setRecyclerViewListTagihan()
    }


    private fun setRecyclerViewListTagihan() {
        rvTagihan = binding.rvTagihanwarga
        rvTagihan.setHasFixedSize(true)
        rvTagihan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterTagihan = RecyclerViewTagihanWarga(
            arrayListOf(),object : RecyclerViewTagihanWarga.OnAdapterListener {
                override fun onBayarTagihan(idTagihan: String) {
                    WargaTagihanPresenter(this@CatatanWarga
                    ).bayarTagihan(
                        getToken(),
                        idTagihan
                    )
                }
            }
        )
        rvTagihan.adapter = adapterTagihan
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindingView(): View {
        binding = FragmentCatatanWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onGetDataSuccess(result: List<GetAllTagihanItem>) {
        adapterTagihan.setData(result)
        if(result.size > 0) {
            binding.tvTidakAdaTagihan.visibility = View.GONE
        }
    }

    override fun onGetDataFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPayBillSuccess(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        WargaTagihanPresenter(this).getAllTagihan(getToken())
    }

    override fun onPayBillFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

}