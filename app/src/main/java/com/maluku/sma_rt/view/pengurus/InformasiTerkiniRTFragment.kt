package com.maluku.sma_rt.view.pengurus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentInformasiMasukBinding
import com.maluku.sma_rt.databinding.FragmentInformasiTerkiniRTBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetInformasiById
import com.maluku.sma_rt.presenter.InformasiPresenter
import com.maluku.sma_rt.view.pengurus.adapter.InformasiMasukAdapter
import com.maluku.sma_rt.view.pengurus.adapter.ListInfoTerkiniAdapter
import com.maluku.sma_rt.view.viewInterface.InformasiInterface


class InformasiTerkiniRTFragment : Fragment(), InformasiInterface {
    private lateinit var binding: FragmentInformasiTerkiniRTBinding
    private lateinit var rvInformasi: RecyclerView
    private lateinit var adapterInformasi: ListInfoTerkiniAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Refresh Data Informasi
        onStart()
        back()
    }

    private fun setRecyclerViewInformasi() {
        rvInformasi = binding.rvListInfoTerkiniRT
        rvInformasi.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL ,false)
        adapterInformasi = ListInfoTerkiniAdapter(arrayListOf())
        rvInformasi.adapter = adapterInformasi
    }

    override fun onStart() {
        super.onStart()
        InformasiPresenter(this).getAllInfoTerkini(getToken())
    }

    private fun bindingView(): View {
        binding = FragmentInformasiTerkiniRTBinding.inflate(layoutInflater)
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
        TODO("Not yet implemented")
    }

    override fun onGetAllInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiSuccess(result: GetInformasiById?) {
        TODO("Not yet implemented")
    }

    override fun onGetInformasiFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetInfoTerkiniSuccess(result: List<GetAllInformasiItem>) {
        setRecyclerViewInformasi()
        adapterInformasi.setData(result as ArrayList<GetAllInformasiItem>)
    }

    override fun onGetInfoTerkiniFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
    }

    override fun onGetKegiatanSuccess(result: List<GetAllInformasiItem>) {
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

    private fun back(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}