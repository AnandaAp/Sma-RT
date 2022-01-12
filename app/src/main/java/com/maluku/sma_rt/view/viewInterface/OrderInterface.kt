package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya
import com.maluku.sma_rt.model.order.GetAllOrderItem
import com.maluku.sma_rt.model.order.GetOrderByIDResponse
import com.maluku.sma_rt.model.order.GetOrderById

interface OrderInterface {
    fun onCreateOrderSuccess(message: String)
    fun onCreateOrderFailure(message: String)
    fun onGetAllOrderSuccess(result: List<GetAllOrderItem>)
    fun onGetAllOrderFailure(message: String)
    fun onGetOrderByIDSuccess(result: GetOrderById)
    fun onGetOrderByIDFailure(message: String)
    fun onOrderProcessSuccess(message: String)
    fun onOrderProcessFailure(message: String)
    fun onOrderCancelSuccess(message: String)
    fun onOrderCancelFailure(message: String)
    fun onOrderCompleteSuccess(message: String)
    fun onOrderCompleteFailure(message: String)
}