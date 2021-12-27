package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.dompetkeluarga.DefaultDompetKeluargaResponse
import com.maluku.sma_rt.model.dompetrt.DefaultDompetRTResponse
import com.maluku.sma_rt.model.dompetrt.GetDompetRTByLoginResponse
import com.maluku.sma_rt.view.viewInterface.DompetRTInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DompetRTPresenter(private var view: DompetRTInterface) {
    fun getDompetRTByLogin(token: String) {
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

    fun withdrawAdmin(
        token: String,
        jumlah: String
    ) {
        RetrofitService
            .getService()
            .withdrawDompetRT(
                "Bearer $token",
                jumlah
            )
            .enqueue(object : Callback<DefaultDompetRTResponse> {
                override fun onResponse(
                    call: Call<DefaultDompetRTResponse>,
                    response: Response<DefaultDompetRTResponse>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful){
                        view.onWithdrawSuccess(message!!)
                    } else {
                        view.onWithdrawFailed(message!!)
                    }
                }

                override fun onFailure(call: Call<DefaultDompetRTResponse>, t: Throwable) {
                    view.onWithdrawFailed("Withdraw failed")
                }
            })
    }
}