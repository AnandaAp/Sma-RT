package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.extentions.UserSession.Companion.SHARED_PREFERENCE_TOKEN_KEY
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaWargaItem
import com.maluku.sma_rt.model.warga.GetAllWargaItem
import com.maluku.sma_rt.presenter.ListKeluargaPresenter
import com.maluku.sma_rt.presenter.ListWargaPresenter
import com.maluku.sma_rt.view.pengurus.adapter.WargaAdapter
import com.maluku.sma_rt.view.viewInterface.ListKeluargaViewInterface
import com.maluku.sma_rt.view.viewInterface.ListWargaViewInterface

private const val TAG = "WARGA FRAGMENT"

class WargaFragment : Fragment(), ListWargaViewInterface, ListKeluargaViewInterface {
    private lateinit var binding: FragmentWargaBinding
    private lateinit var rvWarga: RecyclerView
    private lateinit var adapterWarga: WargaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewWarga()
        ListKeluargaPresenter(requireActivity(),this).getListKeluargaPresenter(getToken())
        ListWargaPresenter(requireActivity(), this).getListWargaPresenter(getToken())
    }

    private fun bindingView(): View {
        binding = FragmentWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setRecyclerViewWarga(){
        rvWarga = binding.rvListWarga
        rvWarga.layoutManager = LinearLayoutManager(requireContext())
        adapterWarga = WargaAdapter(arrayListOf())
        rvWarga.adapter = adapterWarga
    }

    override fun resultListWargaFailed(t: Throwable) {
        Toast.makeText(requireContext(),"Pesan: $t",Toast.LENGTH_LONG).show()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    override fun resultListWargaSuccess(warga: List<GetAllWargaItem>) {
        adapterWarga.setData(warga as ArrayList<GetAllWargaItem>)
    }

    override fun resultListKeluargaSuccess(result: List<GetAllKeluargaWargaItem>) {
        val spListFamily = binding.spListFam
        val listIdKeluarga: ArrayList<String> = ArrayList()
        val listKeluarga: ArrayList<String> = ArrayList()
        for (data in result){
            listIdKeluarga.add(data.id.toString())
            listKeluarga.add(data.nama.toString())
        }
        val adapter = ArrayAdapter(requireActivity(), R.layout.support_simple_spinner_dropdown_item,listKeluarga)
        spListFamily.adapter = adapter
        spListFamily.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var idKeluarga = listIdKeluarga[position]
                Toast.makeText(requireContext(),"${adapterView?.getItemAtPosition(position).toString()} dengan ID $idKeluarga", Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun resultListKeluargaFailed(t: Throwable) {
        Toast.makeText(requireContext(),"Pesan: $t",Toast.LENGTH_LONG).show()
    }
}