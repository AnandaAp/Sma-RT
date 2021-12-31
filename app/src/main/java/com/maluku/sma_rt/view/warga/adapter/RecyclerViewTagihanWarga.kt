package com.maluku.sma_rt.view.warga.adapter

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.presenter.WargaTagihanPresenter
import java.io.File
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewTagihanWarga(
    private val listTagihan: ArrayList<GetAllTagihanItem>,
    val listener: OnAdapterListener
): RecyclerView.Adapter<RecyclerViewTagihanWarga.MyViewHolder>() {

    fun setData(data : List<GetAllTagihanItem>){
        listTagihan.clear()
        listTagihan.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tagihanwarga_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listTagihan[position]

        holder.namaTagihan.text = data.nama
        holder.detailTagihan.text = data.detail
        holder.jumlahTagihan.text = toRupiah(data.jumlah.toString().toDouble())

        if(data.terbayar!!) {
            holder.status.isChecked = true
            holder.namaTagihan.showStrikeThrough(true)
            holder.detailTagihan.showStrikeThrough(true)
            holder.jumlahTagihan.showStrikeThrough(true)
        } else {
            holder.layoutTagihan.setOnClickListener { view ->
                val dialog = Dialog(view.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.custom_dialog_bayartagihan)
                val message = dialog.findViewById<TextView>(R.id.tvKonfirmBayarTagihan)
                val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
                val btnBatal = dialog.findViewById<TextView>(R.id.btn_batal)

                message.text = "Yakin ingin membayar tagihan ${data.nama} senilai ${data.jumlah}?"

                btnOk.setOnClickListener {
                    listener.onBayarTagihan(data.id.toString())
                    dialog.dismiss()
                }
                btnBatal.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return listTagihan.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var namaTagihan: TextView = itemView.findViewById(R.id.tvNamaTagihan)
        var detailTagihan: TextView = itemView.findViewById(R.id.tvDetailTagihan)
        var jumlahTagihan: TextView = itemView.findViewById(R.id.tvJumlahTagihan)
        var status: CheckBox = itemView.findViewById(R.id.cbStatus)
        var layoutTagihan: LinearLayout = itemView.findViewById(R.id.layoutTagihan)
    }

    private fun toRupiah(number: Double): String{
        val localeID =  Locale("in", "ID")
        val numberFormat = NumberFormat.getCurrencyInstance(localeID)
        return numberFormat.format(number).toString()
    }

    interface OnAdapterListener {
        fun onBayarTagihan(idTagihan:String)
    }

    fun TextView.showStrikeThrough(show: Boolean) {
        paintFlags =
            if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }

}