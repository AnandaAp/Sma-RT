package com.maluku.sma_rt.view.warga

import android.graphics.Color
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailPesananUserBinding
import com.maluku.sma_rt.databinding.FragmentIsisaldoTariksaldoBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.GetOrderByIDResponse
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.model.order.ItemOrderItemById
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.AdapterParentProduk
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewDetailPesanan
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "DETAIL PESANAN USER"

class DetailPesananUser : Fragment(), OrderInterface {
    val args: DetailPesananUserArgs by navArgs()

    private lateinit var binding: FragmentDetailPesananUserBinding
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapterOrder: RecyclerViewDetailPesanan

    private var idOrder: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onStart() {
        super.onStart()
        bindData()
        setRecyclerViewListOrder()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStart()
        swipeRefreshLayout()
        goBack()
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setRecyclerViewListOrder() {
        recyclerView = binding.rvDetailpesananuser
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterOrder = RecyclerViewDetailPesanan(
            arrayListOf(), getToken()
        )
        recyclerView.adapter = adapterOrder
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindData() {
        idOrder = args.orderId

        OrderPresenter(this).getOrderByID(getToken(),idOrder)
    }

    private fun setData(result: GetOrderById) {
        // buat dapetin nama toko & gambar
        AndroidNetworking.get("http://smart.aliven.my.id:2001/keluarga/{idKeluarga}")
            .addPathParameter("idKeluarga", result.idKeluargaPenjual)
            .addHeaders("Authorization", "Bearer "+getToken())
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_keluarga_by_id")
                    binding.detailNamatoko.text = data.getString("nama_toko")
                }
                override fun onError(error: ANError?) {
                    binding.detailNamatoko.text = error!!.message.toString()
                }
            })

        binding.tvMetodePembayaran.text = result.pembayaran!!.jenis.toString()
        binding.tvTotalHarga.text = toRupiah(result.hargaTotal.toString().toDouble())

//        binding.tvStatusPesanan.text = result.status.toString()

        if(result.status.toString() == "Di Pesan") {

        } else if(result.status.toString() == "Di Proses") {
            binding.garisDiproses.setBackgroundColor(Color.GREEN)
            binding.checkProses.setImageResource(R.drawable.ic_check_active)
            binding.tvStatusPesanan.text = "Pesananmu sedang diproses penjual"
        } else if(result.status.toString() == "Selesai") {
            binding.garisDiproses.setBackgroundColor(Color.GREEN)
            binding.checkProses.setImageResource(R.drawable.ic_check_active)
            binding.garisSelesai.setBackgroundColor(Color.GREEN)
            binding.checkSelesai.setImageResource(R.drawable.ic_check_active)
            binding.tvStatusPesanan.text = "Pesananmu sudah selesai"
        }

    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailPesananUser_to_riwayatPesananUser)
        }
    }

    private fun bindingView(): View {
        binding = FragmentDetailPesananUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateOrderSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateOrderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderSuccess(result: List<GetAllOrderItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetOrderByIDSuccess(result: GetOrderById) {
        Handler(Looper.getMainLooper()).post(Runnable { //do stuff like remove view etc
            adapterOrder.setData(result.itemOrder as ArrayList<ItemOrderItemById>)
            setData(result)
        })
    }

    override fun onGetOrderByIDFailure(message: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        })
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