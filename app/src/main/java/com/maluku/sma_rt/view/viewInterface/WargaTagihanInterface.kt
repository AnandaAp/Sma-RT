package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem

interface WargaTagihanInterface {
    fun onGetDataSuccess(result: List<GetAllTagihanItem>)
    fun onGetDataFailure(message: String)
    fun onPayBillSuccess(message: String)
    fun onPayBillFailure(message: String)
}