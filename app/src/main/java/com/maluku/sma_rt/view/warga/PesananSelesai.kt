package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananSelesaiBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.AdapterParentListPesananSelesai


class PesananSelesai : Fragment(), OrderInterface {

    private lateinit var binding: FragmentPesananSelesaiBinding
    private lateinit var rvPesananSelesai: RecyclerView
    private lateinit var adapterPesananSelesai: AdapterParentListPesananSelesai

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    private fun bindingView(): View {
        binding = FragmentPesananSelesaiBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        OrderPresenter(this).getAllTokoOrder(getToken(),"3")
        setRecyclerViewPesananSelesai()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStart()
        swipeRefreshLayout()
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setRecyclerViewPesananSelesai() {
        if (context!=null){
            rvPesananSelesai = binding.rvPesananSelesai
            rvPesananSelesai.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL ,false)
            adapterPesananSelesai = AdapterParentListPesananSelesai(
                arrayListOf(),getToken()
            )
            rvPesananSelesai.adapter = adapterPesananSelesai
        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    override fun onCreateOrderSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderSuccess(result: List<GetAllOrderItem>) {
        Handler(Looper.getMainLooper()).post {
            adapterPesananSelesai.setData(result)
        }
    }

    override fun onGetAllOrderFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message", Toast.LENGTH_SHORT)
        }
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