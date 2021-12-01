package com.maluku.sma_rt.view.warga

import android.animation.LayoutTransition
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maluku.sma_rt.R
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewPesananuser
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewToko

class PesananUserPage : Fragment() {

    private lateinit var cardView: CardView
    private lateinit var showButton: ImageButton
    private lateinit var hiddenLayout: LinearLayout

    private lateinit var rvPesananuser: RecyclerView
    private lateinit var adapterPesananuser: RecyclerViewPesananuser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pesanan_user_page, container, false)

        cardView = view.findViewById(R.id.card_view)
        showButton = view.findViewById(R.id.image_button)
        hiddenLayout = view.findViewById(R.id.layout_expand)
        hiddenLayout.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)


        showButton.setOnClickListener {
            if (hiddenLayout.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenLayout.visibility = View.GONE
                showButton.setImageResource(R.drawable.ic_arrow_down)
            } else {
                TransitionManager.beginDelayedTransition(cardView, AutoTransition())
                hiddenLayout.visibility = View.VISIBLE
                showButton.setImageResource(R.drawable.ic_arrow_up)
            }
        }



        rvPesananuser = view.findViewById(R.id.rv_pesnanuser)
        adapterPesananuser = RecyclerViewPesananuser()

        rvPesananuser.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rvPesananuser.setAdapter(adapterPesananuser)

        val btnBack = view.findViewById<TextView>(R.id.btn_back)
        btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserPage_to_jualbeliWarga)
        }




        return view
    }
}
