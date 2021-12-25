package com.maluku.sma_rt.view.warga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R



import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentHomeWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.presenter.ListInfoTerkiniPresenter
import com.maluku.sma_rt.presenter.ListKegiatanPresenter
import com.maluku.sma_rt.presenter.WargaTokoListProdukKeluargaPresenter
import com.maluku.sma_rt.view.viewInterface.ListInfoTerkiniInterface
import com.maluku.sma_rt.view.viewInterface.ListKegiatanInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewKegiatanWarga
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewInfoTerkini
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewProdukpage

private const val TAG = "HOME WARGA"

class HomeWarga : Fragment(), ListInfoTerkiniInterface, ListKegiatanInterface {
    private lateinit var binding: FragmentHomeWargaBinding

    private lateinit var rvInfo: RecyclerView
    private lateinit var rvKegiatan: RecyclerView
    private lateinit var adapterInfo: RecyclerViewInfoTerkini
    private lateinit var adapterKegiatan: RecyclerViewKegiatanWarga


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewInfoTerkini()
        setRecyclerViewKegiatanWarga()
        ListInfoTerkiniPresenter(requireActivity(), this).getListInfoTerkini(getToken())
        ListKegiatanPresenter(requireActivity(), this).getListKegiatan(getToken())
        navigateToMenuLaporan()
        navigateToMenuPersuratan()
        navigateToTopUpSaldo()
    }

    private fun navigateToTopUpSaldo() {
        binding.btnIsisaldo.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_topupSaldo)
        }
    }

    private fun navigateToMenuLaporan() {
        binding.btnLaporan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_laporanWarga)
        }
    }

    private fun navigateToMenuPersuratan() {
        binding.btnPersuratan.setOnClickListener {
            findNavController().navigate(R.id.action_homeWarga_to_persuratanWarga)
        }
    }

    private fun setRecyclerViewInfoTerkini() {
        rvInfo = binding.rvInfo
        rvInfo.setHasFixedSize(true)
        rvInfo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        adapterInfo = RecyclerViewInfoTerkini(
            arrayListOf()
        )
        rvInfo.adapter = adapterInfo
    }

    private fun setRecyclerViewKegiatanWarga() {
        rvKegiatan = binding.rvKegiatan
        rvKegiatan.setHasFixedSize(true)
        rvKegiatan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        adapterKegiatan = RecyclerViewKegiatanWarga(
            arrayListOf()
        )
        rvKegiatan.adapter = adapterKegiatan
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindingView(): View {
        binding = FragmentHomeWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun showDataInfoTerkini(info: List<GetAllInformasiItem>) {
        updateDataInfoTerkini(info)
    }

    override fun updateDataInfoTerkini(info: List<GetAllInformasiItem>) {
        adapterInfo.setData(info as ArrayList<GetAllInformasiItem>)
    }

    override fun showDataKegiatan(kegiatan: List<GetAllInformasiItem>) {
        updateDataKegiatan(kegiatan)
    }

    override fun updateDataKegiatan(kegiatan: List<GetAllInformasiItem>) {
        adapterKegiatan.setData((kegiatan as ArrayList<GetAllInformasiItem>))
    }

}