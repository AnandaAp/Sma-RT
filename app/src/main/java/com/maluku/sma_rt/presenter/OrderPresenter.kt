package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.CreateOrderResponse
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderPresenter(private val view: OrderInterface) {

    fun createOrder(
        token: String,
        order: ArrayList<CreateOrderBody>
    ){
        RetrofitService
            .getService()
            .createOrder(
                "Bearer $token",
                order
            ).enqueue(
                object : Callback<CreateOrderResponse>{
                    override fun onResponse(
                        call: Call<CreateOrderResponse>,
                        response: Response<CreateOrderResponse>
                    ) {
                        val message = response.body()?.message.toString()
                        view.onCreateOrderSuccess(message)
                    }

                    override fun onFailure(call: Call<CreateOrderResponse>, t: Throwable) {
                        val message = t.message.toString()
                        view.onCreateOrderFailed(message)
                    }
                }
            )
    }
}