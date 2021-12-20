package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem

interface WargaTagihanInterface {
    fun onGetDataSuccess(message: String,list: ArrayList<GetAllTagihanItem?>?)
    fun onGetDataFailed(message: String)
    fun onBillPaidSuccess(message: String)
    fun onBillPaidFailed(message: String)
}