package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.presenter.KeranjangPresenter
import com.maluku.sma_rt.presenter.OrderPresenter
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewTerlaris(
    private val listProduk: ArrayList<GetAllProdukItem>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<RecyclerViewTerlaris.MyViewHolder>() {

    fun setData(data : List<GetAllProdukItem>){
        listProduk.clear()
        listProduk.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.terlaris_recycler_vertical_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listProduk[position]
        holder.namaBarang.text = data.nama
        holder.hargaBarang.text = toRupiah(data.harga.toString().toDouble())
        if(data.detail!!.length > 22) {
            holder.namaToko.text = data.detail.toString().take(22) + "..."
        } else {
            holder.namaToko.text = data.detail.toString()
        }

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarBarang)
        }.addOnFailureListener {

        }

        holder.layoutDaftarproduk.setOnClickListener { view ->
            val dialog = BottomSheetDialog(view.context, R.style.AppBottomSheetDialogTheme)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.bottomsheet_detailproduk)

            val nama = dialog.findViewById<TextView>(R.id.namaproduk)
            val gambar = dialog.findViewById<ImageView>(R.id.gambarproduk)
            val harga = dialog.findViewById<TextView>(R.id.hargaproduk)
            val deskripsi = dialog.findViewById<TextView>(R.id.deskripsiproduk)

            nama!!.text = data.nama.toString()
            harga!!.text = toRupiah(data.harga.toString().toDouble())
            deskripsi!!.text = data.detail.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.gambar}")
            Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
            val localFile = File.createTempFile("tempFile","jpg")
            storageRef.getFile(localFile).addOnSuccessListener {
                Glide.with(holder.itemView)
                    .load(localFile.path)
                    .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                    .into(gambar!!)
            }.addOnFailureListener {
            }

            val btnTambah = dialog.findViewById<TextView>(R.id.btn_keranjang)
            btnTambah?.setOnClickListener {
                val item = CreateOrderBody(data.id.toString(), 1, "")
                listener.onAddToChart(item)
            }


            val btnClose = dialog.findViewById<ImageButton>(R.id.btn_close)

            btnClose?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

    }



    override fun getItemCount(): Int {
        return listProduk.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaBarang: TextView = itemView.findViewById(R.id.tv_namabarang)
        var hargaBarang: TextView = itemView.findViewById(R.id.tv_hargabarang)
        var namaToko: TextView = itemView.findViewById(R.id.tv_namatoko)
        var gambarBarang: ImageView = itemView.findViewById(R.id.imageBarang)
        var layoutDaftarproduk: ConstraintLayout = itemView.findViewById(R.id.layoutDaftarproduk)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    interface OnAdapterListener {
        fun onAddToChart(item: CreateOrderBody)
    }
}