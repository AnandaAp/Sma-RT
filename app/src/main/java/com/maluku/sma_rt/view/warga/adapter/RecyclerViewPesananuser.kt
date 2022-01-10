package com.maluku.sma_rt.view.warga.adapter

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import org.json.JSONObject
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewPesananuser(
    private val listItem: ArrayList<ItemKeranjangItem>,
    private val token: String,
    val listener: RecyclerViewPesananuser.OnAdapterListener
): RecyclerView.Adapter<RecyclerViewPesananuser.MyViewHolder>() {

    fun setData(data : List<ItemKeranjangItem>){
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pesananuser_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listItem[position]

        holder.harga.text = toRupiah(data.hargaTotal.toString().toDouble())
        holder.jumlah.text = data.jumlah.toString()

        // buat dapetin nama produk
        AndroidNetworking.get("http://smart.aliven.my.id:2001/produk/{idProduk}")
            .addPathParameter("idProduk", data.idProduk)
            .addHeaders("Authorization", "Bearer ${token}")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    val data: JSONObject = response.getJSONObject("get_produk_by_id")
                    holder.nama.text = data.getString("nama")
                    // Firebase Storage
                    val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.getString("gambar")}")
                    Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
                    val localFile = File.createTempFile("tempFile","jpg")
                    storageRef.getFile(localFile).addOnSuccessListener {
                        Glide.with(holder.itemView)
                            .load(localFile.path)
                            .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                            .into(holder.gambar)
                    }.addOnFailureListener {

                    }
                }
                override fun onError(error: ANError?) {
                    holder.nama.text = error!!.message.toString()
                }
            })

        holder.btnTambah.setOnClickListener {
            listener.onAdd(data.idProduk.toString())
        }

        holder.btnKurang.setOnClickListener {
            listener.onReduce(data.idProduk.toString())
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nama: TextView = itemView.findViewById(R.id.namaprodukpesanan)
        var harga: TextView = itemView.findViewById(R.id.hargaprodukpesanan)
        var jumlah: TextView = itemView.findViewById(R.id.tv_jumlah)
        var gambar: ImageView = itemView.findViewById(R.id.imageprodukpesanan)
        var btnTambah: ImageButton = itemView.findViewById(R.id.btn_tambah)
        var btnKurang: ImageButton = itemView.findViewById(R.id.btn_kurang)
        var layout: CardView = itemView.findViewById(R.id.cvitem)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    interface OnAdapterListener {
        fun onAdd(idProduk:String)
        fun onReduce(idProduk:String)
    }

}