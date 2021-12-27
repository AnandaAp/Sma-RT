package com.maluku.sma_rt.view.warga

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentIsisaldoTariksaldoBinding


class IsisaldoTariksaldo : Fragment() {

    private lateinit var binding: FragmentIsisaldoTariksaldoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        topupSaldo()
        tarikSaldo()

    }

    private fun bindingView(): View {
        binding = FragmentIsisaldoTariksaldoBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_isisaldoTariksaldo_to_kelolaToko)
        }
    }

    private fun topupSaldo() {
        binding.cardIsisaldo.setOnClickListener{
            findNavController().navigate(R.id.action_isisaldoTariksaldo_to_topupSaldo)
        }
    }

    private fun tarikSaldo() {
        binding.cardTariksaldo.setOnClickListener{
            findNavController().navigate(R.id.action_isisaldoTariksaldo_to_tarikSaldo)
        }
    }


}