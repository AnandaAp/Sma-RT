package com.maluku.sma_rt.view.pengurus.bottomnavigation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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
    private var idKeluarga: String? = null
    private var nama: String? = null
    private var isSpinnerInitial = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Refresh Data Warga
        onStart()
        binding.etCariWarga.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                nama = s.toString()
                ListWargaPresenter(requireActivity(), this@WargaFragment).getListWargaPresenter(getToken(), idKeluarga, nama)
            }
        })

    }

    override fun onStart() {
        super.onStart()
        ListKeluargaPresenter(this).getListKeluargaPresenter(getToken())
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

    override fun resultListWargaFailure(message: String) {
        Toast.makeText(requireContext(),"Pesan: $message",Toast.LENGTH_LONG).show()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    override fun resultListWargaSuccess(warga: List<GetAllWargaItem>) {
        setRecyclerViewWarga()
        adapterWarga.setData(warga as ArrayList<GetAllWargaItem>)
    }

    override fun resultListKeluargaSuccess(result: List<GetAllKeluargaWargaItem>) {
        val spListFamily = binding.spListFam
        val listIdKeluarga: ArrayList<String?> = arrayListOf(idKeluarga)
        val listKeluarga: ArrayList<String> = arrayListOf("Pilih Keluarga")
        for (data in result){
            listIdKeluarga.add(data.id.toString())
            listKeluarga.add(data.nama.toString())
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item,listKeluarga)
        spListFamily.adapter = adapter
        spListFamily.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(isSpinnerInitial)
                {
                    isSpinnerInitial = false
                }
                else  {
                    idKeluarga = listIdKeluarga[position]
                }
                ListWargaPresenter(requireActivity(), this@WargaFragment).getListWargaPresenter(getToken(), idKeluarga, nama)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    override fun resultListKeluargaFailure(message: String) {
        Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
    }

}