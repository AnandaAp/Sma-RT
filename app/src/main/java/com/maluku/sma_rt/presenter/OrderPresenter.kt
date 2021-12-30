package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.CreateOrderResponse
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class OrderPresenter(private val view: OrderInterface) {

    @DelicateCoroutinesApi
    fun createOrder(
        token: String,
        order: ArrayList<CreateOrderBody>
    ){
        /*RetrofitService
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
            )*/
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .createOrder(
                    "Bearer $token",
                    order
                ).awaitResponse()
            if(res.isSuccessful){
                val message = res.body()!!.message.toString()
                view.onCreateOrderSuccess(message)
            }
            else{
                val message = res.errorBody()!!.string()
                view.onCreateOrderFailed(message)
            }
        }
    }
}