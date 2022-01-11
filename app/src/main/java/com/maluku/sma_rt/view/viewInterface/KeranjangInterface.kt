package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya
import com.maluku.sma_rt.model.keranjang.GetKeranjangById
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import com.maluku.sma_rt.model.keranjang.KeranjangCheckout
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.GetAllOrderItem

interface KeranjangInterface {
    fun onGetKeranjangSuccess(result: GetKeranjangById)
    fun onGetKeranjangFailure(message: String)
    fun onGetKeranjangCheckoutSuccess(result: GetKeranjangById)
    fun onGetKeranjangCheckoutFailure(message: String)
    fun onAddProductKeranjangSuccess(message: String)
    fun onAddProductKeranjangFailure(message: String, item: CreateOrderBody)
    fun onUpdateKeranjangSuccess(message: String)
    fun onUpdateKeranjangFailure(message: String)
    fun onAddQuantitySuccess(message: String)
    fun onAddQuantityFailure(message: String)
    fun onReduceQuantitySuccess(message: String)
    fun onReduceQuantityFailure(message: String)
}