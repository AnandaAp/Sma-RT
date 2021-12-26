package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.dompetrt.GetDompetRTByLoginResponse
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminRTKasPresenter(private var view: DompetRTInterface) {
    fun getDompetRT(token: String) {
        RetrofitService
            .getService()
            .getDompetRT("Bearer $token")
            .enqueue(object : Callback<GetDompetRTByLoginResponse> {
                override fun onResponse(
                    call: Call<GetDompetRTByLoginResponse>,
                    response: Response<GetDompetRTByLoginResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getDompetById
                        view.onGetAllDataSuccess(result)
                    } else{
                        view.onGetAllDataFailed(response.message().toString())
                    }
                }

                override fun onFailure(call: Call<GetDompetRTByLoginResponse>, t: Throwable) {
                    view.onGetAllDataFailed(t.message.toString())
                }
            })
    }
}