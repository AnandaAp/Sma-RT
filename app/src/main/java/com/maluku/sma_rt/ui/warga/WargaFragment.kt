package com.maluku.sma_rt.ui.warga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maluku.sma_rt.databinding.FragmentWargaBinding

class WargaFragment : Fragment() {

    private lateinit var wargaViewModel: WargaViewModel
    private var _binding: FragmentWargaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wargaViewModel =
            ViewModelProvider(this).get(WargaViewModel::class.java)

        _binding = FragmentWargaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView10
        wargaViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}