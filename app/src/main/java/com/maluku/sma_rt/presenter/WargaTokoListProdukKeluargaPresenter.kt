package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaResponse
import com.maluku.sma_rt.view.viewInterface.ListProdukInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WargaTokoListProdukKeluargaPresenter(private val activity: Activity, private var view: ListProdukInterface) {
    fun getListProdukByToken(token: String) {
        RetrofitService
            .getService()
            .getAllProdukKeluarga("Bearer $token")
            .enqueue(object : Callback<GetAllProdukKeluargaResponse> {
                override fun onResponse(
                    call: Call<GetAllProdukKeluargaResponse>,
                    response: Response<GetAllProdukKeluargaResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllProdukKeluarga as List<GetAllProdukKeluargaItem>
                        view.resultSuccess(result)
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetAllProdukKeluargaResponse>, t: Throwable) {
                    view.resultFailed(t)
                }
            })
    }
}