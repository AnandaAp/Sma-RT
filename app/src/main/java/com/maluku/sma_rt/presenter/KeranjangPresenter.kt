package com.maluku.sma_rt.presenter

import android.app.Activity
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllKeluargaResponse
import com.maluku.sma_rt.model.keranjang.*
import com.maluku.sma_rt.model.order.CreateOrderBody
import com.maluku.sma_rt.model.produk.GetAllProdukItem
import com.maluku.sma_rt.model.produk.GetAllProdukResponse
import com.maluku.sma_rt.view.viewInterface.KeranjangInterface
import com.maluku.sma_rt.view.viewInterface.WargaJualBeliInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse


class KeranjangPresenter(private var view: KeranjangInterface) {
    fun getKeranjang(token: String) {
        RetrofitService
            .getService()
            .getKeranjang("Bearer $token")
            .enqueue(object : Callback<GetKeranjangResponse> {
                override fun onResponse(
                    call: Call<GetKeranjangResponse>,
                    response: Response<GetKeranjangResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getKeranjangById as GetKeranjangById
                        view.onGetKeranjangSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetKeranjangFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetKeranjangResponse>, t: Throwable) {
                    view.onGetKeranjangFailure(t.message.toString())
                }
            })
    }

    fun getKeranjangCheckout(token: String) {
        RetrofitService
            .getService()
            .getKeranjang("Bearer $token")
            .enqueue(object : Callback<GetKeranjangResponse> {
                override fun onResponse(
                    call: Call<GetKeranjangResponse>,
                    response: Response<GetKeranjangResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getKeranjangById as GetKeranjangById
                        view.onGetKeranjangCheckoutSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetKeranjangCheckoutFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetKeranjangResponse>, t: Throwable) {
                    view.onGetKeranjangFailure(t.message.toString())
                }
            })
    }

    fun tambahProdukKeKeranjang(
        token: String,
        item: CreateOrderBody
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .tambahProdukKeKeranjang(
                    "Bearer $token",
                    item
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onAddProductKeranjangSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onAddProductKeranjangFailure(message, item)
            }
        }
    }

    fun updateKeranjang(
        token: String,
        item: ArrayList<CreateOrderBody>
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .updateKeranjang(
                    "Bearer $token",
                    item
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onUpdateKeranjangSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onUpdateKeranjangFailure(message)
            }
        }
    }

    fun tambahJumlahProduk(
        token: String,
        idProduk: String
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .tambahJumlahProduk(
                    "Bearer $token",
                    idProduk
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onAddQuantitySuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onAddQuantityFailure(message)
            }
        }
    }

    fun kurangJumlahProduk(
        token: String,
        idProduk: String
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .kurangJumlahProduk(
                    "Bearer $token",
                    idProduk
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onReduceQuantitySuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onReduceQuantityFailure(message)
            }
        }
    }

    fun hapusProduk(
        token: String,
        idProduk: String
    ){
        GlobalScope.launch(Dispatchers.IO) {
            val res = RetrofitService
                .getService()
                .hapusProdukKeranjang(
                    "Bearer $token",
                    idProduk
                ).awaitResponse()
            if(res.isSuccessful){
                val result = res.body()?.message.toString()
                view.onRemoveItemSuccess(result)
            }
            else{
                val jObjError = JSONObject(res.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onRemoveItemFailure(message)
            }
        }
    }

}