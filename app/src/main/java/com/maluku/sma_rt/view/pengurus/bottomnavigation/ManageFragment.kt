package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentManageBinding


class ManageFragment : Fragment() {

    private lateinit var binding: FragmentManageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateManageToTambahInformasi()
        navigateManageToLaporan()
        navigateManageToKas()
        navigateManageToTambahKeluarga()
        navigateManageToPersuratan()
    }

    private fun navigateManageToTambahInformasi(){
        binding.btnInformasi.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_manage_to_informasiFragment)
        }
    }

    private fun navigateManageToLaporan() {
        binding.btnLaporan.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_manage_to_laporanFragment)
        }
    }

    private fun navigateManageToTambahKeluarga() {
        binding.btnToTambahKeluarga.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_manage_to_tambahKeluargaFragment)
        }
    }

    private fun navigateManageToKas() {
        binding.btnKas.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_manage_to_kasFragment)
        }
    }

    private fun navigateManageToPersuratan() {
        binding.btnPersuratan.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_manage_to_suratFragment)
        }
    }

    private fun bindingView(): View {
        binding = FragmentManageBinding.inflate(layoutInflater)
        return binding.root
    }

}