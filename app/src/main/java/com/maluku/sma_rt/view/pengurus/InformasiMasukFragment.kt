package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.databinding.FragmentInformasiMasukBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.pengurus.adapter.InformasiMasukAdapter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface

class InformasiMasukFragment : Fragment(), InformasiInterface{
    private lateinit var binding: FragmentInformasiMasukBinding
    private lateinit var rvInformasi: RecyclerView
    private lateinit var adapterInformasi: InformasiMasukAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewInformasi()
        InformasiPresenter(this).getAllInformasi(getToken())
        // Refresh Data Informasi
        onStart()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            fragmentManager!!.beginTransaction().detach(this).attach(this).commit()
        }
    }

    private fun setRecyclerViewInformasi() {
        if (context!=null){
            rvInformasi = binding.rvInformasiMasuk
            rvInformasi.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL ,false)
            adapterInformasi = InformasiMasukAdapter(arrayListOf())
            rvInformasi.adapter = adapterInformasi
        }
    }

    override fun onStart() {
        super.onStart()
        InformasiPresenter(this).getAllInformasi(getToken())
    }

    private fun bindingView(): View {
        binding = FragmentInformasiMasukBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        return token
    }

    override fun onCreateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onCreateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }


    override fun onGetAllInformasiSuccess(result: List<GetAllInformasiItem>) {
        adapterInformasi.setData(result as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetAllInformasiFailure(message: String) {
        if (context!=null){
            Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
        }
    }

    override fun onGetInformasiSuccess(result: GetInformasiById?) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniSuccess(data: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanSuccess(data: List<GetAllInformasiItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetKegiatanFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onUpdateInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

}