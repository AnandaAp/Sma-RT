package com.maluku.sma_rt.presenter

import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetAllInformasiResponse
import com.maluku.sma_rt.view.viewInterface.ListAllInformationInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListAllInformationPresenter(private var view: ListAllInformationInterface) {
    fun getListAllInformation(token: String) {
        RetrofitService
            .getService()
            .getAllInformasi("Bearer $token")
            .enqueue(object : Callback<GetAllInformasiResponse> {
                override fun onResponse(
                    call: Call<GetAllInformasiResponse>,
                    response: Response<GetAllInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                        view.resultListAllInformationSuccess(result)
                    } else{
                        view.resultListAllInformationFailed(response.message().toString())
                    }
                }

                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.resultListAllInformationFailed("Pesan: ${t.message.toString()}")
                }
            })
    }
}