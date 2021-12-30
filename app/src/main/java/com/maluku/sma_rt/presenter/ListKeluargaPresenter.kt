package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaWargaItem
import com.maluku.sma_rt.model.keluarga.GetListKeluargaResponse
import com.maluku.sma_rt.view.viewInterface.ListKeluargaViewInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

@DelicateCoroutinesApi
class ListKeluargaPresenter(private var view: ListKeluargaViewInterface) {
    fun getListKeluargaPresenter(token: String) {
        /*RetrofitService
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
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.resultListKeluargaFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetListKeluargaResponse>, t: Throwable) {
                    view.resultListKeluargaFailure(t.message.toString())
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getListKeluarga("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getAllKeluargaWarga as List<GetAllKeluargaWargaItem>
                view.resultListKeluargaSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.resultListKeluargaFailure(message)
            }
        }
    }
}