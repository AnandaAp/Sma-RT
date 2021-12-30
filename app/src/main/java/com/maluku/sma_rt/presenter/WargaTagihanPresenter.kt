package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.tagihan.GetAllTagihanItem
import com.maluku.sma_rt.model.tagihan.GetAllTagihanResponse
import com.maluku.sma_rt.model.updateanddelete.OnDataResponse
import com.maluku.sma_rt.view.viewInterface.WargaTagihanInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WargaTagihanPresenter(private val view: WargaTagihanInterface) {
    fun bayarTagihan(
        token: String,
        idTagihan: String
    ){
        RetrofitService
            .getService()
            .bayarTagihan(
                "Bearer $token",
                idTagihan
            )
            .enqueue(object : Callback<OnDataResponse>{
                override fun onResponse(
                    call: Call<OnDataResponse>,
                    response: Response<OnDataResponse>
                ) {
                    if (response.isSuccessful){
                        val message = response.body()?.message.toString()
                        view.onPayBillSuccess(message)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onPayBillFailure(message)
                    }
                }

                override fun onFailure(call: Call<OnDataResponse>, t: Throwable) {
                    val message = t.message.toString()
                    view.onPayBillFailure(message)
                }
            })
    }

    fun getAllTagihan(token: String){
        RetrofitService
            .getService()
            .getAllTagihan(
                "Bearer $token"
            )
            .enqueue(object : Callback<GetAllTagihanResponse>{
                override fun onResponse(
                    call: Call<GetAllTagihanResponse>,
                    response: Response<GetAllTagihanResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllTagihan as List<GetAllTagihanItem>
                        view.onGetDataSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetDataFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllTagihanResponse>, t: Throwable) {
                    view.onGetDataFailure(t.message.toString())
                }
            })
    }
}