package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.warga.GetAllWargaItem
import com.maluku.sma_rt.model.warga.GetAllWargaResponse
import com.maluku.sma_rt.view.viewInterface.ListWargaViewInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListWargaPresenter(private val activity: Activity, private var view: ListWargaViewInterface) {
    fun getListWargaPresenter(token: String) {
        RetrofitService
            .getService()
            .getListWarga("Bearer $token")
            .enqueue(object : Callback<GetAllWargaResponse> {
                override fun onResponse(
                    call: Call<GetAllWargaResponse>,
                    response: Response<GetAllWargaResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllWarga as List<GetAllWargaItem>
                        view.resultListWargaSuccess(result)
                        Toast.makeText(activity,"Pesan: ${response.message()}",Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(activity,"Pesan: ${response.message()}",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllWargaResponse>, t: Throwable) {
                    view.resultListWargaFailed(t)
                }
            })
    }
}