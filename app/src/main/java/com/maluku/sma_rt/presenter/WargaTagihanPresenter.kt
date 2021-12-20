package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.tagihan.GetAllTagihanResponse
import com.maluku.sma_rt.model.updateanddelete.OnDataResponse
import com.maluku.sma_rt.view.viewInterface.WargaTagihanInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WargaTagihanPresenter(private val view: WargaTagihanInterface) {
    fun paidBill(token: String){
        RetrofitService
            .getService()
            .bayarTagihan(
                "Bearer $token",
                token
            )
            .enqueue(object : Callback<OnDataResponse>{
                override fun onResponse(
                    call: Call<OnDataResponse>,
                    response: Response<OnDataResponse>
                ) {
                    val message = response.body()?.message.toString()
                    view.onBillPaidSuccess(message)
                }

                override fun onFailure(call: Call<OnDataResponse>, t: Throwable) {
                    val message = t.message.toString()
                    view.onBillPaidFailed(message)
                }
            })
    }

    fun getAllTagihan(idKeluarga: String,token: String){
        RetrofitService
            .getService()
            .getAllTagihan(
                "Bearer $token",
                idKeluarga
            )
            .enqueue(object : Callback<GetAllTagihanResponse>{
                override fun onResponse(
                    call: Call<GetAllTagihanResponse>,
                    response: Response<GetAllTagihanResponse>
                ) {
                    val message = response.body()?.message.toString()
                    val list =  response.body()?.getAllTagihan
                    view.onGetDataSuccess(message,list)
                }

                override fun onFailure(call: Call<GetAllTagihanResponse>, t: Throwable) {
                    view.onGetDataFailed(t.message.toString())
                }
            })
    }
}