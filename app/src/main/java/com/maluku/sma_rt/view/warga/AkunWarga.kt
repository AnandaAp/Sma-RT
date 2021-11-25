package com.maluku.sma_rt.view.warga

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentAkunWargaBinding
import com.maluku.sma_rt.databinding.FragmentLoginWargaBinding
import com.maluku.sma_rt.view.activity.SecondActivity
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewAkunmenu
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfo
import com.maluku.sma_rt.view.warga.model.AkunItem

class AkunWarga : Fragment() {

    private lateinit var binding: FragmentAkunWargaBinding

    private lateinit var rvAkunmenu: RecyclerView
    private lateinit var adapterAkunmenu: RecyclerViewAkunmenu

    private var arrayList:ArrayList<AkunItem> ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkunWargaBinding.inflate(layoutInflater)

        binding.btnBack.setOnClickListener{
            val intent = Intent (activity, SecondActivity::class.java)
            startActivity(intent)
        }

        rvAkunmenu = binding.rvAkunmenu
        rvAkunmenu.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rvAkunmenu.setHasFixedSize(true)
        arrayList = ArrayList()
        arrayList = setDataInList()
        adapterAkunmenu = activity?.let { RecyclerViewAkunmenu(it, arrayList!!) }!!
        rvAkunmenu.adapter = adapterAkunmenu


        return binding.root
    }

    private fun setDataInList() : ArrayList<AkunItem>{
        var items:ArrayList<AkunItem> = ArrayList()

        items.add(AkunItem(R.drawable.akunitem1, "Kelola Toko"))
        items.add(AkunItem(R.drawable.akunitem2, "Edit Profile"))
        items.add(AkunItem(R.drawable.akunitem3, "Pengaturan"))

        return items
    }

}