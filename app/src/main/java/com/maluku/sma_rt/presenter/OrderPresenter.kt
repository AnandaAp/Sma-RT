package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import com.maluku.sma_rt.model.keranjang.KeranjangCheckout
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.view.viewInterface.OrderInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.awaitResponse

class OrderPresenter(private val view: OrderInterface) {

    @DelicateCoroutinesApi
    fun createOrderPakaiSaldo(
        token: String,
        order: ArrayList<KeranjangCheckout>
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .createOrderPakaiSaldo(
                    "Bearer $token",
                    order
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onCreateOrderSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onCreateOrderFailure(message)
            }
        }
    }

    fun createOrderPakaiCOD(
        token: String,
        order: ArrayList<KeranjangCheckout>
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .createOrderPakaiCOD(
                    "Bearer $token",
                    order
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onCreateOrderSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onCreateOrderFailure(message)
            }
        }
    }

    fun getAllOrder(token: String) {
        GlobalScope.launch(Dispatchers.IO){
            val res = RetrofitService
                .getService()
                .getAllOrder("Bearer $token")
                .awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.getAllOrder as List<GetAllOrderItem>
                view.onGetAllOrderSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetAllOrderFailure(message)
            }
        }
    }

    fun getAllTokoOrder(
        token: String,
        status: String? = null
    ) {
        GlobalScope.launch(Dispatchers.IO){
            val res = RetrofitService
                .getService()
                .getAllTokoOrder("Bearer $token",status)
                .awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.getAllOrder as List<GetAllOrderItem>
                view.onGetAllOrderSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetAllOrderFailure(message)
            }
        }
    }

    fun prosesOrder(
        token: String,
        idOrder: String
    ) {
        GlobalScope.launch(Dispatchers.IO){
            val res = RetrofitService
                .getService()
                .prosesOrder(
                    "Bearer $token",
                    idOrder
                )
                .awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onOrderProcessSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onOrderProcessFailure(message)
            }
        }
    }

    fun cancelOrder(
        token: String,
        idOrder: String
    ) {
        GlobalScope.launch(Dispatchers.IO){
            val res = RetrofitService
                .getService()
                .cancelOrder(
                    "Bearer $token",
                    idOrder
                )
                .awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onOrderCancelSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onOrderCancelFailure(message)
            }
        }
    }

    fun selesaiOrder(
        token: String,
        idOrder: String
    ) {
        GlobalScope.launch(Dispatchers.IO){
            val res = RetrofitService
                .getService()
                .selesaiOrder(
                    "Bearer $token",
                    idOrder
                )
                .awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onOrderCompleteSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onOrderCompleteFailure(message)
            }
        }
    }

}