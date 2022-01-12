package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaResponse
import com.maluku.sma_rt.model.keluarga.GetKeluargaById
import com.maluku.sma_rt.model.order.GetOrderById
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.model.produk.GetAllProdukResponse
import com.maluku.sma_rt.view.viewInterface.WargaJualBeliInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse


class WargaJualBeliPresenter(private var view: WargaJualBeliInterface) {
    fun getAllKeluarga(token: String, nama: String?) {
        RetrofitService
            .getService()
            .getAllKeluarga("Bearer $token",nama)
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

    fun getAllProduk(token: String, idKeluarga: String?, nama: String?) {
        RetrofitService
            .getService()
            .getAllProduk("Bearer $token",idKeluarga,nama)
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

    fun getKeluargaByID(token: String, id_keluarga: String) {
        GlobalScope.launch(Dispatchers.IO){
            val res = RetrofitService
                .getService()
                .getKeluargaById("Bearer $token", id_keluarga)
                .awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.getKeluargaById as GetKeluargaById
                view.onGetKeluargaByIDSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetKeluargaByIDFailure(message)
            }
        }
    }
}