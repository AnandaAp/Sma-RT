package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaWargaItem
import com.maluku.sma_rt.model.keluarga.GetListKeluargaResponse
import com.maluku.sma_rt.model.warga.GetAllWargaItem
import com.maluku.sma_rt.model.warga.GetAllWargaResponse
import com.maluku.sma_rt.view.viewInterface.ListKeluargaViewInterface
import com.maluku.sma_rt.view.viewInterface.ListWargaViewInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListKeluargaPresenter(private val activity: Activity, private var view: ListKeluargaViewInterface) {
    fun getListKeluargaPresenter(token: String) {
        RetrofitService
            .getService()
            .getListKeluarga("Bearer $token")
            .enqueue(object : Callback<GetListKeluargaResponse> {
                override fun onResponse(
                    call: Call<GetListKeluargaResponse>,
                    response: Response<GetListKeluargaResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllKeluargaWarga as List<GetAllKeluargaWargaItem>
                        view.resultListKeluargaSuccess(result)
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetListKeluargaResponse>, t: Throwable) {
                    view.resultListKeluargaFailed(t)
                }
            })
    }
}