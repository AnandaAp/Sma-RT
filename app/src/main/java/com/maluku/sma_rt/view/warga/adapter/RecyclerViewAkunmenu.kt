package com.maluku.sma_rt.view.warga.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.KelolaToko
import com.maluku.sma_rt.view.warga.model.AkunItem

class RecyclerViewAkunmenu(
    var context: Context,
    var arrayList: ArrayList<AkunItem>
    ): RecyclerView.Adapter<RecyclerViewAkunmenu.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_akunmenu_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int) {

        var akunItem:AkunItem = arrayList.get(position)

        holder.title.text = akunItem.titleItem
        holder.icons.setImageResource(akunItem.iconsItem!!)

        holder.itemView.setOnClickListener(object :View.OnClickListener {
            override fun onClick(v: View?) {
                val activity = v !!.context as AppCompatActivity
                val kelolaToko = KelolaToko()
                activity.supportFragmentManager.beginTransaction().replace(R.id.layout_kelola,kelolaToko).addToBackStack(null).commit()

            }
        })

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.findViewById(R.id.tv_akunitem)
        val icons: ImageView = itemView.findViewById(R.id.image_akunitem)

    }




}