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
import com.maluku.sma_rt.api.notification.NotificationData
import com.maluku.sma_rt.api.notification.PushNotification
import com.maluku.sma_rt.databinding.FragmentPesananDiprosesBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.persuratan.GetAllPersuratanItem
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.AdapterParentListPesananDiproses

class PesananDiproses : Fragment(), OrderInterface {
    private lateinit var binding: FragmentPesananDiprosesBinding
    private lateinit var rvPesananDiproses: RecyclerView
    private lateinit var adapterPesananDiproses: AdapterParentListPesananDiproses
    private var idOrder: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    private fun bindingView(): View {
        binding = FragmentPesananDiprosesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        OrderPresenter(this).getAllTokoOrder(getToken(),"2")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewPesananDiproses()
        onStart()
    }

    private fun setRecyclerViewPesananDiproses() {
        if (context!=null){
            rvPesananDiproses = binding.rvPesananDiproses
            rvPesananDiproses.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL ,false)
            adapterPesananDiproses = AdapterParentListPesananDiproses(
                arrayListOf(),getToken(),object : AdapterParentListPesananDiproses.OnAdapterListener{
                    override fun onSelesai(orderId: String) {
                        idOrder = orderId
                        OrderPresenter(this@PesananDiproses).selesaiOrder(
                            getToken(),orderId
                        )
                    }
                }
            )
            rvPesananDiproses.adapter = adapterPesananDiproses
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
            adapterPesananDiproses.setData(result)
        }
    }

    override fun onGetAllOrderFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message",Toast.LENGTH_SHORT)
        }
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
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message",Toast.LENGTH_LONG)
            OrderPresenter(this).getAllTokoOrder(getToken(),"2")
        }
    }

    override fun onOrderCompleteFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Pesan: $message",Toast.LENGTH_SHORT)
        }
    }
}