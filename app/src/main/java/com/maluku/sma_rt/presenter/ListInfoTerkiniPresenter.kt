package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetAllInformasiResponse
import com.maluku.sma_rt.view.viewInterface.ListInfoTerkiniInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListInfoTerkiniPresenter(private val activity: Activity, private val view: ListInfoTerkiniInterface) {
    fun getListInfoTerkiniPresenter(token: String) {
        RetrofitService
            .getService()
            .getAllInfoTerkini("Bearer $token")
            .enqueue(object : Callback<GetAllInformasiResponse> {
                override fun onResponse(
                    call: Call<GetAllInformasiResponse>,
                    response: Response<GetAllInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                        view.onGetInfoTerkiniSuccess(result)
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.onGetInfoTerkiniFailed(t.message.toString())
                }
            })
    }
}