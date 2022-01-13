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
import com.maluku.sma_rt.databinding.FragmentPesananDiprosesBinding
import com.maluku.sma_rt.databinding.FragmentPesananMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.AdapterParentListPesananDiproses
import com.maluku.sma_rt.view.warga.adapter.AdapterParentListPesananMasuk


class PesananMasuk : Fragment(), OrderInterface {
    private lateinit var binding: FragmentPesananMasukBinding
    private lateinit var rvPesananMasuk: RecyclerView
    private lateinit var adapterPesananMasuk: AdapterParentListPesananMasuk
    private var idOrder: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    private fun bindingView(): View {
        binding = FragmentPesananMasukBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        OrderPresenter(this).getAllTokoOrder(getToken(),"1")
        setRecyclerViewPesananMasuk()
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

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        return preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
    }

    private fun setRecyclerViewPesananMasuk() {
        if (context!=null){
            rvPesananMasuk = binding.rvParentlistpesanan
            rvPesananMasuk.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL ,false)
            adapterPesananMasuk = AdapterParentListPesananMasuk(
                arrayListOf(),getToken(),object : AdapterParentListPesananMasuk.OnAdapterListener{
                    override fun onProses(orderId: String) {
                        idOrder = orderId
                        OrderPresenter(this@PesananMasuk).prosesOrder(
                            getToken(),orderId
                        )
                    }

                    override fun onCancel(orderId: String) {
                        idOrder = orderId
                        OrderPresenter(this@PesananMasuk).cancelOrder(
                            getToken(),orderId
                        )
                    }
                }
            )
            rvPesananMasuk.adapter = adapterPesananMasuk
        }
    }

    override fun onCreateOrderSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderSuccess(result: List<GetAllOrderItem>) {
        Handler(Looper.getMainLooper()).post {
            adapterPesananMasuk.setData(result)
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
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message", Toast.LENGTH_LONG)
            OrderPresenter(this).getAllTokoOrder(getToken(),"1")
        }
    }

    override fun onOrderProcessFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message", Toast.LENGTH_SHORT)
        }
    }

    override fun onOrderCancelSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message", Toast.LENGTH_LONG)
            OrderPresenter(this).getAllTokoOrder(getToken(),"1")
        }
    }

    override fun onOrderCancelFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message", Toast.LENGTH_SHORT)
        }
    }

    override fun onOrderCompleteSuccess(message: String) {

    }

    override fun onOrderCompleteFailure(message: String) {

    }

}