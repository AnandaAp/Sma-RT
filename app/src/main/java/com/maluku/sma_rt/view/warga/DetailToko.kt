package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailTokoBinding
import com.maluku.sma_rt.databinding.FragmentLaporanSayaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.keranjang.GetKeranjangById
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.presenter.KeranjangPresenter
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.presenter.WargaJualBeliPresenter
import com.maluku.sma_rt.view.viewInterface.KeranjangInterface
import com.maluku.sma_rt.view.viewInterface.WargaJualBeliInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewJenisProduk
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanSaya
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTerlaris
import java.io.File

private const val TAG = "DETAIL TOKO"

class DetailToko : Fragment(), WargaJualBeliInterface, KeranjangInterface {
    val args: DetailTokoArgs by navArgs()

    private lateinit var binding: FragmentDetailTokoBinding
    private lateinit var rvJenisProduk: RecyclerView
    private lateinit var adapterJenisProduk: RecyclerViewJenisProduk

    private var nama: String? = null
    private var idKeluarga: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onStart() {
        super.onStart()
        bindData()
        setRecyclerViewJenisProduk()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onStart()
        swipeRefreshLayout()
        btnBack()
    }

    private fun swipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            onStart()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun bindData() {
        idKeluarga = args.keluargaId

        WargaJualBeliPresenter(this).getKeluargaByID(getToken(),idKeluarga)
        WargaJualBeliPresenter(this).getAllProduk(getToken(),idKeluarga,nama)
    }

    private fun setRecyclerViewJenisProduk() {
        rvJenisProduk = binding.rvjenisproduk
        rvJenisProduk.setHasFixedSize(true)
        rvJenisProduk.layoutManager = GridLayoutManager(context, 2)
        adapterJenisProduk = RecyclerViewJenisProduk(
            arrayListOf(),object : RecyclerViewTerlaris.OnAdapterListener{
                override fun onAddToChart(item: CreateOrderBody) {
                    KeranjangPresenter(this@DetailToko).tambahProdukKeKeranjang(
                        getToken(),
                        item
                    )
                }
            }
        )
        rvJenisProduk.adapter = adapterJenisProduk
    }

    private fun setData(result: GetKeluargaById) {
        binding.detailnamatoko.text = result.namaToko.toString()
        binding.textView5.text = result.alamat.toString()

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("toko/${result.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.detailgambartoko)
        }.addOnFailureListener {

        }
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
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


    private fun btnBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailToko_to_jualbeliWarga)
        }
    }

    private fun bindingView(): View {
        binding = FragmentDetailTokoBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onGetAllTokoSuccess(result: List<GetAllKeluargaItem>) {
    }

    override fun onGetAllTokoFailure(message: String) {
    }

    override fun onGetAllProdukSuccess(result: List<GetAllProdukItem>) {
        adapterJenisProduk.setData(result as ArrayList<GetAllProdukItem>)
    }

    override fun onGetAllProdukFailure(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun onGetKeluargaByIDSuccess(result: GetKeluargaById) {
        Handler(Looper.getMainLooper()).post {
            setData(result)
        }
    }

    override fun onGetKeluargaByIDFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetKeranjangSuccess(result: GetKeranjangById) {
        TODO("Not yet implemented")
    }

    override fun onGetKeranjangFailure(message: String) {
        TODO("Not yet implemented")
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
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onUpdateKeranjangFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
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
