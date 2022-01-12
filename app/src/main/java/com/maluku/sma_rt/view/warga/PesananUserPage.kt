package com.maluku.sma_rt.view.warga

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananUserPageBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keranjang.GetKeranjangById
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import com.maluku.sma_rt.model.keranjang.KeranjangCheckout
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.presenter.KeranjangPresenter
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.view.viewInterface.KeranjangInterface
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.AdapterParentListPesananDiproses
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewPesananuser
import org.json.JSONObject
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "PESANAN USER PAGE"

class PesananUserPage : Fragment(), OrderInterface, KeranjangInterface {
    private lateinit var binding: FragmentPesananUserPageBinding

    private lateinit var cardView: CardView
    private lateinit var showButton: ImageButton
    private lateinit var hiddenLayout: LinearLayout

    private lateinit var rvPesananuser: RecyclerView
    private lateinit var adapterPesananuser: RecyclerViewPesananuser

    private var bayarPakaiSaldo: Boolean = false
    private var keranjang: ArrayList<KeranjangCheckout> = arrayListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        KeranjangPresenter(this).getKeranjang(getToken())
        setRecylerViewPesanan()
        goBack()
        metodePembayaran(view)
        submitPesan()
    }

    private fun setRecylerViewPesanan() {
        rvPesananuser = binding.rvPesnanuser
        adapterPesananuser = RecyclerViewPesananuser(
            arrayListOf(),
            getToken(),object : RecyclerViewPesananuser.OnAdapterListener{
                override fun onAdd(idProduk: String) {
                    KeranjangPresenter(this@PesananUserPage).tambahJumlahProduk(
                        getToken(),idProduk
                    )
                }

                override fun onReduce(idProduk: String) {
                    KeranjangPresenter(this@PesananUserPage).kurangJumlahProduk(
                        getToken(),idProduk
                    )
                }

                override fun onRemove(idProduk: String) {
                    KeranjangPresenter(this@PesananUserPage).hapusProduk(
                        getToken(),idProduk
                    )
                }
            }
        )

        rvPesananuser.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rvPesananuser.setAdapter(adapterPesananuser)

        hideJikaKeranjangKosong(true)

    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserPage_to_jualbeliWarga)
        }
    }

    private fun submitPesan() {
        binding.btnPesan.setOnClickListener {
            if(binding.rbCOD.isChecked || binding.rbSaldo.isChecked) {
                KeranjangPresenter(this).getKeranjangCheckout(getToken())
            } else {
                Toast.makeText(requireContext(),"Pilih metode pembayaran terlebih dahulu!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createOrderPakaiSaldo() {
        OrderPresenter(this).createOrderPakaiSaldo(getToken(), keranjang)
    }

    private fun createOrderPakaiCOD() {
        OrderPresenter(this).createOrderPakaiCOD(getToken(), keranjang)
    }

    private fun metodePembayaran(view: View) {
        cardView = view.findViewById(R.id.card_view)
        showButton = view.findViewById(R.id.image_button)
        hiddenLayout = view.findViewById(R.id.layout_expand)
        hiddenLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        showButton.setOnClickListener {
            if (hiddenLayout.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenLayout.visibility = View.GONE
                showButton.setImageResource(R.drawable.ic_arrow_down)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenLayout.visibility = View.VISIBLE
                showButton.setImageResource(R.drawable.ic_arrow_up)
            }
        }
        binding.rbCOD.setOnClickListener {
            binding.rbCOD.isChecked = true
            binding.rbSaldo.isChecked = false
            bayarPakaiSaldo = false
        }

        binding.rbSaldo.setOnClickListener {
            binding.rbCOD.isChecked = false
            binding.rbSaldo.isChecked = true
            bayarPakaiSaldo = true
        }

    }

    private fun hideJikaKeranjangKosong(hide: Boolean) {
        if(hide) {
            binding.layoutMetodePembayaran.visibility = View.GONE
            binding.layoutTotalharga.visibility = View.GONE
            binding.tvKeranjangKosong.visibility = View.VISIBLE
        } else {
            binding.layoutMetodePembayaran.visibility = View.VISIBLE
            binding.layoutTotalharga.visibility = View.VISIBLE
            binding.tvKeranjangKosong.visibility = View.GONE
        }
    }

    private fun resetKeranjang() {
        val arrOrder = ArrayList<CreateOrderBody>()
        KeranjangPresenter(this).updateKeranjang(getToken(), arrOrder)
    }

    private fun bindingView(): View {
        binding = FragmentPesananUserPageBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    override fun onCreateOrderSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
            val direction = PesananUserPageDirections.actionPesananUserPageToRiwayatPesananUser()
            findNavController().navigate(direction)
        }
    }

    override fun onCreateOrderFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetAllOrderSuccess(result: List<GetAllOrderItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderFailure(message: String) {
        TODO("Not yet implemented")
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

    override fun onGetKeranjangSuccess(result: GetKeranjangById) {
        val items = result.itemKeranjang as? List<ItemKeranjangItem>
        if(!items.isNullOrEmpty()) {
            adapterPesananuser.setData(items!!)
            hideJikaKeranjangKosong(false)
        }
        binding.tvTotalpembayaran.text = toRupiah(result.hargaTotal.toString().toDouble())
    }

    override fun onGetKeranjangFailure(message: String) {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    override fun onGetKeranjangCheckoutSuccess(result: GetKeranjangById) {
        val items = result.itemKeranjang as? ArrayList<KeranjangCheckout>
        if(!items.isNullOrEmpty()) {
            keranjang = items
            if(binding.rbCOD.isChecked) {
                createOrderPakaiCOD()
            } else if(binding.rbSaldo.isChecked) {
                createOrderPakaiSaldo()
            }
            resetKeranjang()
        } else {
            Toast.makeText(context,"Keranjang masih kosong!", Toast.LENGTH_LONG).show()
        }

    }

    override fun onGetKeranjangCheckoutFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message,Toast.LENGTH_SHORT)
        }
    }

    override fun onAddProductKeranjangSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddProductKeranjangFailure(message: String, item: CreateOrderBody) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onUpdateKeranjangSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpdateKeranjangFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onAddQuantitySuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            KeranjangPresenter(this).getKeranjang(getToken())
        }
    }

    override fun onAddQuantityFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onReduceQuantitySuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            KeranjangPresenter(this).getKeranjang(getToken())
            setRecylerViewPesanan()
        }
    }

    override fun onReduceQuantityFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRemoveItemSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,"Produk berhasil dihapus dari keranjang",Toast.LENGTH_SHORT).show()
            KeranjangPresenter(this).getKeranjang(getToken())
            setRecylerViewPesanan()
        }
    }

    override fun onRemoveItemFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }
    }
}
