package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentRiwayatPesananUserBinding
import com.maluku.sma_rt.view.warga.adapter.AdapterParentProduk
import com.maluku.sma_rt.view.warga.adapter.ModelChildProduk
import com.maluku.sma_rt.view.warga.adapter.ModelParentProduk

class RiwayatPesananUser : Fragment() {

    private lateinit var binding: FragmentRiwayatPesananUserBinding
    private lateinit var recyclerView: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goback()
        initRecycler()



    }

    private fun goback() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatPesananUser_to_jualbeliWarga)
        }
    }




    private fun initRecycler(){
        recyclerView = binding.rvParent
        val list = mutableListOf<ModelParentProduk>()
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = AdapterParentProduk(list)

        }

        list.add(
            ModelParentProduk("Toko Agus",
                mutableListOf(
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Sampah"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Manis"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Enak")
                ))
        )

        list.add(
            ModelParentProduk("Toko Jarwo",
                mutableListOf(
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Goreng"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Manis")
                ))
        )

        list.add(
            ModelParentProduk("Toko Nunung",
                mutableListOf(
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Ikan"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Aren")
                ))
        )

        list.add(
            ModelParentProduk("Toko Ucok",
                mutableListOf(
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Ikan"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Aren"),
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Ikan"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Aren"),
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Ikan"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Aren"),
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Ikan"),
                    ModelChildProduk(R.drawable.gambargula,"Gulaku Aren")

                ))
        )

        list.add(
            ModelParentProduk("Toko Waluyo",
                mutableListOf(
                    ModelChildProduk(R.drawable.gambarminyak, "Minyak Tanah"),
                    ModelChildProduk(R.drawable.gambargula,"Gula Pahit")
                ))
        )





    }


    private fun bindingView(): View {
        binding = FragmentRiwayatPesananUserBinding.inflate(layoutInflater)
        return binding.root
    }

}