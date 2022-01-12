package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentDetailTokoBinding
import com.maluku.sma_rt.databinding.FragmentLaporanSayaBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewJenisProduk
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewLaporanSaya


class DetailToko : Fragment() {

    private lateinit var binding: FragmentDetailTokoBinding
    private lateinit var rvJenisProduk: RecyclerView
    private lateinit var adapterJenisProduk: RecyclerViewJenisProduk


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewJenisProduk()
        btnBack()
    }

    private fun setRecyclerViewJenisProduk() {
        rvJenisProduk = binding.rvjenisproduk
        rvJenisProduk.setHasFixedSize(true)
        rvJenisProduk.layoutManager = GridLayoutManager(context, 2)
        adapterJenisProduk = RecyclerViewJenisProduk()
        rvJenisProduk.adapter = adapterJenisProduk
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

}
