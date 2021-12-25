package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentHomeBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.presenter.ListInfoTerkiniPresenter
import com.maluku.sma_rt.presenter.ListKegiatanPresenter
import com.maluku.sma_rt.view.pengurus.adapter.GaleriAdapter
import com.maluku.sma_rt.view.pengurus.adapter.InfoAdapter
import com.maluku.sma_rt.view.viewInterface.ListInfoTerkiniInterface
import com.maluku.sma_rt.view.viewInterface.ListKegiatanInterface

class HomeFragment : Fragment(), ListKegiatanInterface, ListInfoTerkiniInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rvGaleri: RecyclerView
    private lateinit var adapterGaleri: GaleriAdapter
    private lateinit var rvInfo: RecyclerView
    private lateinit var adapterInfo: InfoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewInformasi()
        navigateDashboardToTambahKelurga()
        navigateDashboardToSurat()
        navigateDashboardToInformasi()
    }

    override fun onStart() {
        super.onStart()
        ListKegiatanPresenter(requireActivity(),this).getListKegiatanPresenter(getToken())
        ListInfoTerkiniPresenter(requireActivity(),this).getListInfoTerkiniPresenter(getToken())
    }

    private fun setRecyclerViewInformasi(){
        rvGaleri = binding.rvGaleriWarga
        adapterGaleri = GaleriAdapter(arrayListOf())
        rvInfo = binding.rvInfoTerkini
        adapterInfo = InfoAdapter(arrayListOf())
        rvGaleri.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvGaleri.adapter = adapterGaleri
        rvInfo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        rvInfo.adapter = adapterInfo
    }

    private fun navigateDashboardToTambahKelurga() {
        binding.btnTambahKeluarga.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_tambahKeluargaFragment)
        }
    }

    private fun navigateDashboardToSurat() {
        binding.btnSurat.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_suratFragment)
        }
    }

    private fun navigateDashboardToInformasi() {
        binding.btnInformasi.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_home_to_informasiFragment)
        }
    }

    private fun bindingView(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        return token
    }

    override fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>) {
        adapterInfo.setData(result as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetInfoTerkiniFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message", Toast.LENGTH_LONG).show()
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
        adapterGaleri.setData(result as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetKegiatanFailed(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message", Toast.LENGTH_LONG).show()
    }

}