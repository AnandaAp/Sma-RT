package com.maluku.sma_rt.view.viewInterface

import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem


//interface Tagihan yang bisa dipakai oleh Admin dan Warga
interface AdminTagihanInterface {
    fun onCreateSuccess(message: String)
    fun onCreateFailed(message: String)
    fun onGetDataSuccess(message: String, list: List<GetAllTagihanItem>)
    fun onGetDataFailed(message: String)
}