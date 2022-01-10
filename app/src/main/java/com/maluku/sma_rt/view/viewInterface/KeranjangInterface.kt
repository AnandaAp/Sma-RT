package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.keluarga.GetKeluargaSaya
import com.maluku.sma_rt.model.keranjang.GetKeranjangById
import com.maluku.sma_rt.model.keranjang.ItemKeranjangItem
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.order.GetAllOrderItem

interface KeranjangInterface {
    fun onGetKeranjangSuccess(result: List<ItemKeranjangItem>)
    fun onGetKeranjangFailure(message: String)
    fun onAddProductKeranjangSuccess(message: String)
    fun onAddProductKeranjangFailure(message: String, item: CreateOrderBody)
    fun onUpdateKeranjangSuccess(message: String)
    fun onUpdateKeranjangFailure(message: String)
}