package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaResponse
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.model.produk.GetAllProdukResponse
import com.maluku.sma_rt.view.viewInterface.WargaJualBeliInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WargaJualBeliPresenter(private var view: WargaJualBeliInterface) {
    fun getAllKeluarga(token: String) {
        RetrofitService
            .getService()
            .getAllKeluarga("Bearer $token")
            .enqueue(object : Callback<GetAllKeluargaResponse> {
                override fun onResponse(
                    call: Call<GetAllKeluargaResponse>,
                    response: Response<GetAllKeluargaResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllKeluarga as List<GetAllKeluargaItem>
                        view.onGetAllTokoSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllTokoFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllKeluargaResponse>, t: Throwable) {
                    view.onGetAllTokoFailure(t.message.toString())
                }
            })
    }

    fun getAllProduk(token: String) {
        RetrofitService
            .getService()
            .getAllProduk("Bearer $token")
            .enqueue(object : Callback<GetAllProdukResponse> {
                override fun onResponse(
                    call: Call<GetAllProdukResponse>,
                    response: Response<GetAllProdukResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllProduk as List<GetAllProdukItem>
                        view.onGetAllProdukSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllProdukFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllProdukResponse>, t: Throwable) {
                    view.onGetAllProdukFailure(t.message.toString())
                }
            })
    }
}