package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.tagihan.CreateTagihanResponse
import com.maluku.sma_rt.model.tagihan.GetAllTagihanResponse
import com.maluku.sma_rt.view.viewInterface.AdminTagihanInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTagihanPresenter(private val view: AdminTagihanInterface) {
    fun createProduk(
        nama: String,
        detail: String,
        jumlah: Double,
        token: String
    ){
        RetrofitService
            .getService()
            .crateTagihan(
                "Bearer $token",
                nama,
                detail,
                jumlah
            )
            .enqueue(object : Callback<CreateTagihanResponse>{
                override fun onResponse(
                    call: Call<CreateTagihanResponse>,
                    response: Response<CreateTagihanResponse>
                ) {
                    val message = response.body()?.message.toString()
                    view.onCreateSuccess(message)
                }

                override fun onFailure(call: Call<CreateTagihanResponse>, t: Throwable) {
                    view.onCreateFailed(t.message.toString())
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