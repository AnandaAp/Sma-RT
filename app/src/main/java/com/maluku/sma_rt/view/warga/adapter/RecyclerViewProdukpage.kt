package com.maluku.sma_rt.view.warga.adapter

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.view.warga.ProdukPageDirections
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewProdukpage(
    private val listProduk: ArrayList<GetAllProdukKeluargaItem>
): RecyclerView.Adapter<RecyclerViewProdukpage.MyViewHolder>() {

    fun setData(data : List<GetAllProdukKeluargaItem>){
        listProduk.clear()
        listProduk.addAll(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaProduk: TextView = itemView.findViewById(R.id.tv_namaproduk)
        var hargaProduk: TextView = itemView.findViewById(R.id.tv_hargaproduk)
        var gambarProduk: ImageView = itemView.findViewById(R.id.img_produk)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.produkpage_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listProduk[position]
        holder.namaProduk.text = data.nama.toString()
        holder.hargaProduk.text = toRupiah(data.harga.toString().toDouble())

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("produk/${data.gambar}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
//            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
//            holder.gambarProduk.setImageBitmap(bitmap)
            // Tampilkan gambar dengan Glide
            Glide.with(holder.itemView)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(holder.gambarProduk)
        }.addOnFailureListener {

        }

        // Edit Produk
        holder.itemView.setOnClickListener { view ->
            val direction = ProdukPageDirections
                .actionProdukPageToEditProduk(
                    data.id.toString(),data.nama.toString(),data.detail.toString(),data.gambar.toString(),data.harga.toString()
                )
            view.findNavController().navigate(direction)
        }

    }

    override fun getItemCount(): Int {
        return listProduk.size
    }


    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    interface OnAdapterListener {
        fun onDelete(productId:String)
    }
}