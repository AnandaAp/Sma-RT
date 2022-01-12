package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.maluku.sma_rt.databinding.FragmentRiwayatPesananUserBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.GetOrderByIDResponse
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.presenter.WargaTagihanPresenter
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.*

private const val TAG = "RIWAYAT PESANAN USER"

class RiwayatPesananUser : Fragment(), OrderInterface {

    private lateinit var binding: FragmentRiwayatPesananUserBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapterOrder: AdapterParentProduk

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        OrderPresenter(this).getAllOrder(getToken())
        goback()
        setRecyclerViewListOrder()
//        initRecycler()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun goback() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatPesananUser_to_jualbeliWarga)
        }
    }

    private fun setRecyclerViewListOrder() {
        recyclerView = binding.rvParent
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterOrder = AdapterParentProduk(
            arrayListOf(), getToken()
        )
        recyclerView.adapter = adapterOrder
    }


    private fun bindingView(): View {
        binding = FragmentRiwayatPesananUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateOrderSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderSuccess(result: List<GetAllOrderItem>) {
        Handler(Looper.getMainLooper()).post(Runnable { //do stuff like remove view etc
            adapterOrder.setData(result as ArrayList<GetAllOrderItem>)
        })
    }

    override fun onGetAllOrderFailure(message: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        })
    }

    override fun onGetOrderByIDSuccess(result: GetOrderById) {
        TODO("Not yet implemented")
    }

    override fun onGetOrderByIDFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderProcessSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderProcessFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCancelSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCancelFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCompleteSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCompleteFailure(message: String) {
        TODO("Not yet implemented")
    }
}