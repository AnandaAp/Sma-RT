package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.tagihan.CreateTagihanResponse
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.model.tagihan.GetAllTagihanResponse
import com.maluku.sma_rt.view.viewInterface.AdminTagihanInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminTagihanPresenter(private val view: AdminTagihanInterface) {
    fun createTagihan(
        nama: String,
        detail: String,
        jumlah: String,
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
                    if (response.isSuccessful){
                        val message = response.body()?.message.toString()
                        view.onCreateSuccess(message)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onCreateFailed(message)
                    }
                }
                override fun onFailure(call: Call<CreateTagihanResponse>, t: Throwable) {
                    view.onCreateFailed(t.message.toString())
                }
            })
    }
    fun getAllTagihan(token: String){
        RetrofitService
            .getService()
            .getAllTagihanRT(
                "Bearer $token"
            )
            .enqueue(object : Callback<GetAllTagihanResponse>{
                override fun onResponse(
                    call: Call<GetAllTagihanResponse>,
                    response: Response<GetAllTagihanResponse>
                ) {
                    if (response.isSuccessful){
                        val message = response.body()?.message.toString()
                        val list =  response.body()?.getAllTagihan as List<GetAllTagihanItem>
                        view.onGetDataSuccess(message,list)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetDataFailed(message)
                    }
                }

                override fun onFailure(call: Call<GetAllTagihanResponse>, t: Throwable) {
                    view.onGetDataFailed(t.message.toString())
                }
            })
    }
}