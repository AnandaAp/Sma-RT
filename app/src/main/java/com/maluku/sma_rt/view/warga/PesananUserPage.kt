package com.maluku.sma_rt.view.warga

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentPesananUserPageBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.presenter.OrderPresenter
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import com.maluku.sma_rt.view.warga.adapter.RecyclerViewPesananuser
import org.json.JSONObject

private const val TAG = "PESANAN USER PAGE"

class PesananUserPage : Fragment(), OrderInterface {
    private lateinit var binding: FragmentPesananUserPageBinding

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
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecylerViewPesanan()
        goBack()
        submitPesan()
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
    }

    private fun setRecylerViewPesanan() {
        rvPesananuser = binding.rvPesnanuser
        adapterPesananuser = RecyclerViewPesananuser()

        rvPesananuser.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rvPesananuser.setAdapter(adapterPesananuser)
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_pesananUserPage_to_jualbeliWarga)
        }
    }

    private fun submitPesan() {
        binding.btnPesan.setOnClickListener {
            val arrOrder = ArrayList<CreateOrderBody>()
            val data = CreateOrderBody("01FQNTQ9KYRFW9YNMYGAXP1R8M", 1, "topping coklat")
            arrOrder.add(data)
            arrOrder.add(CreateOrderBody("01FQNTQ9M1WA992RJY56VTJ4YX", 2, "topping keju"))
            OrderPresenter(this).createOrderPakaiSaldo(getToken(), arrOrder)
            //            findNavController().navigate(R.id.action_pesananUserPage_to_pesananUserMenunggu)
        }
    }

    private fun bindingView(): View {
        binding = FragmentPesananUserPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreateOrderSuccess(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOrderFailure(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onGetAllOrderSuccess(result: List<GetAllOrderItem>) {
        TODO("Not yet implemented")
    }

    override fun onGetAllOrderFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderProcessSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderProcessFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCancelSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCancelFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCompleteSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onOrderCompleteFailure(message: String) {
        TODO("Not yet implemented")
    }
}
