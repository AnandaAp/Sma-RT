package com.maluku.sma_rt.view.warga

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentCatatanWargaBinding
import com.maluku.sma_rt.databinding.FragmentProdukPageBinding
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewTagihanWarga

class CatatanWarga : Fragment() {


    private lateinit var binding: FragmentCatatanWargaBinding
    private lateinit var rvTagihan: RecyclerView
    private lateinit var adapterTagihan: RecyclerViewTagihanWarga


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewListTagihan()
        bayarTagihanWarga()
    }


    private fun setRecyclerViewListTagihan() {
        rvTagihan = binding.rvTagihanwarga
        rvTagihan.setHasFixedSize(true)
        rvTagihan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        adapterTagihan = RecyclerViewTagihanWarga()
        rvTagihan.adapter = adapterTagihan
    }

    private fun bayarTagihanWarga() {
        binding.btnBayar.setOnClickListener {
            val dialog = BottomSheetDialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.fragment_detail_produk)


            val btnClose = dialog.findViewById<TextView>(R.id.btn_close)
            if (btnClose != null) {
                btnClose.setOnClickListener {
                    dialog.dismiss()
                }
            }

            dialog.setCancelable(false)

            dialog.show()
        }
    }




    private fun bindingView(): View {
        binding = FragmentCatatanWargaBinding.inflate(layoutInflater)
        return binding.root
    }

}