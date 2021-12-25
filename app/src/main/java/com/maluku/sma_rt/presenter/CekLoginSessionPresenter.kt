package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetAllInformasiResponse
import com.maluku.sma_rt.view.viewInterface.CekLoginSessionInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekLoginSessionPresenter(private val activity: Activity, private var view: CekLoginSessionInterface) {
    fun cekLoginSession(token: String) {
        RetrofitService
            .getService()
            .getAllInformasi("Bearer $token")
            .enqueue(object : Callback<GetAllInformasiResponse> {
                override fun onResponse(
                    call: Call<GetAllInformasiResponse>,
                    response: Response<GetAllInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        val code = response.body()?.code
                        if(code == 200) {
                            view.resultSuccess(true)
                        } else {
                            view.resultSuccess(false)
                        }
                    } else{
                        view.resultSuccess(false)
                    }
                }
                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.resultFailed(t)
                }
            })
    }
}