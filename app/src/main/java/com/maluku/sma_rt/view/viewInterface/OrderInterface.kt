package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya
import com.maluku.sma_rt.model.order.GetAllOrderItem

interface OrderInterface {
    fun onCreateOrderSuccess(message: String)
    fun onCreateOrderFailure(message: String)
    fun onGetAllOrderSuccess(result: List<GetAllOrderItem>)
    fun onGetAllOrderFailure(message: String)
    fun onOrderProcessSuccess(message: String)
    fun onOrderProcessFailure(message: String)
    fun onOrderCancelSuccess(message: String)
    fun onOrderCancelFailure(message: String)
    fun onOrderCompleteSuccess(message: String)
    fun onOrderCompleteFailure(message: String)
}