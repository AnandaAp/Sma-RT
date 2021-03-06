package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentJualbeliWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_NAME_KEY
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.keranjang.GetKeranjangById
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import com.maluku.sma_rt.model.keranjang.KeranjangCheckout
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.presenter.KeranjangPresenter
import com.maluku.sma_rt.presenter.ListWargaPresenter
import com.maluku.sma_rt.presenter.WargaJualBeliPresenter
import com.maluku.sma_rt.view.activity.MainActivity
import com.maluku.sma_rt.view.viewInterface.KeranjangInterface
import com.maluku.sma_rt.view.viewInterface.WargaJualBeliInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTerlaris
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewToko

private const val TAG = "JUAL BELI WARGA"

class JualbeliWarga : Fragment(), WargaJualBeliInterface, KeranjangInterface{
    private lateinit var binding: FragmentJualbeliWargaBinding

    private lateinit var rvToko: RecyclerView
    private lateinit var rvTerlaris: RecyclerView
    private lateinit var adapterToko: RecyclerViewToko
    private lateinit var adapterTerlaris: RecyclerViewTerlaris

    private var idKeluarga: String? = null
    private var nama: String? = null

    private var namaWarga: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onStart() {
        super.onStart()
        WargaJualBeliPresenter(this).getAllKeluarga(getToken(),nama)
        WargaJualBeliPresenter(this).getAllProduk(getToken(),idKeluarga, nama)
        KeranjangPresenter(this).getKeranjang(getToken())
        setRecyclerViewToko()
        setRecyclerViewProduk()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStart()
        swipeRefreshLayout()
        pencarian()
        setDataWarga()
        navigateToBasket()
        navigateToHistoryOrder()
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun pencarian() {
        binding.edNamaproduk.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                nama = s.toString()
                WargaJualBeliPresenter( this@JualbeliWarga).getAllProduk(getToken(), idKeluarga, nama)
            }
        })
    }

    private fun setDataWarga() {
        val userSession = UserSession(requireActivity())
        namaWarga = userSession
            .getValueString(SHARED_PREFERENCE_NAME_KEY)
        binding.tvNama.text = "Hi, ${namaWarga}"
    }

    private fun navigateToBasket() {
        binding.troli.setOnClickListener {
            findNavController().navigate(R.id.action_jualbeliWarga_to_pesananUserPage)
        }
    }
    private fun navigateToHistoryOrder() {
        binding.history.setOnClickListener {
            findNavController().navigate(R.id.action_jualbeliWarga_to_riwayatPesananUser)
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
            arrayListOf(),object : RecyclerViewTerlaris.OnAdapterListener{
                override fun onAddToChart(item: CreateOrderBody) {
                    KeranjangPresenter(this@JualbeliWarga).tambahProdukKeKeranjang(
                        getToken(),
                        item
                    )
                }
            }
        )
        rvTerlaris.adapter = adapterTerlaris
    }

    private fun replaceKeranjang(item: CreateOrderBody) {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.custom_dialog_tambahkeranjang)
        val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
        val btnBatal = dialog.findViewById<TextView>(R.id.btn_batal)

        btnOk.setOnClickListener {
            val arrOrder = ArrayList<CreateOrderBody>()
            arrOrder.add(item)
            KeranjangPresenter(this).updateKeranjang(getToken(), arrOrder)
            dialog.dismiss()
        }
        btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun bindingView(): View {
        binding = FragmentJualbeliWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onGetAllTokoSuccess(result: List<GetAllKeluargaItem>) {
        adapterToko.setData(result as ArrayList<GetAllKeluargaItem>)
    }

    override fun onGetAllTokoFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetAllProdukSuccess(result: List<GetAllProdukItem>) {
        adapterTerlaris.setData(result as ArrayList<GetAllProdukItem>)
    }

    override fun onGetAllProdukFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetKeluargaByIDSuccess(result: GetKeluargaById) {
        TODO("Not yet implemented")
    }

    override fun onGetKeluargaByIDFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKeranjangSuccess(result: GetKeranjangById) {
        val items = result.itemKeranjang as? List<ItemKeranjangItem>
        if(!items.isNullOrEmpty()) {
            binding.tvNumbertroli.text = items.size.toString()
        } else {
            binding.trolinumber.visibility = View.GONE
        }
    }

    override fun onGetKeranjangFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    override fun onGetKeranjangCheckoutSuccess(result: GetKeranjangById) {
        TODO("Not yet implemented")
    }

    override fun onGetKeranjangCheckoutFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddProductKeranjangSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            KeranjangPresenter(this).getKeranjang(getToken())
        }
    }

    override fun onAddProductKeranjangFailure(message: String, item: CreateOrderBody) {
        Handler(Looper.getMainLooper()).post {
//            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            replaceKeranjang(item)
        }
    }

    override fun onUpdateKeranjangSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
            KeranjangPresenter(this).getKeranjang(getToken())
        }
    }

    override fun onUpdateKeranjangFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onAddQuantitySuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddQuantityFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onReduceQuantitySuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onReduceQuantityFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRemoveItemSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onRemoveItemFailure(message: String) {
        TODO("Not yet implemented")
    }

}