package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.produk.CreateProductResponse
import com.maluku.sma_rt.model.produk.DeleteProductByIDResponse
import com.maluku.sma_rt.model.produk.UpdateProductByIDResponse
import com.maluku.sma_rt.model.warga.UpdateWargaResponse
import com.maluku.sma_rt.model.warga.WargaGetDataLoginResponse
import com.maluku.sma_rt.view.viewInterface.WargaEditProfileInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

private const val TAG = "EDIT PROFILE"

@DelicateCoroutinesApi
class WargaEditProfilePresenter(private val view: WargaEditProfileInterface) {

    fun getDataLogin(token: String) {
        /*RetrofitService
            .getService()
            .getDataLoginWarga("Bearer $token")
            .enqueue(object : Callback<WargaGetDataLoginResponse> {
                override fun onResponse(
                    call: Call<WargaGetDataLoginResponse>,
                    response: Response<WargaGetDataLoginResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getWargaById
                        view.onGetDataSuccess(result)
//                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    } else{
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val message = jObjError.getString("message")
                        view.onGetDataFailure(message)
                    }
                }

                override fun onFailure(call: Call<WargaGetDataLoginResponse>, t: Throwable) {
                    view.onGetDataFailure(t.message.toString())
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getDataLoginWarga("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getWargaById
                view.onGetDataSuccess(result)
//                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetDataFailure(message)
            }
        }
    }

    fun updateProfile(
        token: String,
        wargaId: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        gambar: String
    ) {
        /*RetrofitService
            .getService()
            .updateWarga(
                "Bearer $token",
                wargaId,
                gender,
                no_hp,
                nama,
                email,
                gambar
            )
            .enqueue(object : Callback<UpdateWargaResponse> {
                override fun onResponse(
                    call: Call<UpdateWargaResponse>,
                    response: Response<UpdateWargaResponse>
                ) {
                    if (response.isSuccessful){
                        view.onUpdateSuccess("Berhasil memperbaharui data!")
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetDataFailure(message)
                    }
                }

                override fun onFailure(call: Call<UpdateWargaResponse>, t: Throwable) {
                    view.onUpdateFailure(t.message.toString())
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .updateWarga(
                    "Bearer $token",
                    wargaId,
                    gender,
                    no_hp,
                    nama,
                    email,
                    gambar
                ).awaitResponse()
            if (response.isSuccessful){
                view.onUpdateSuccess("Berhasil memperbaharui data!")
            } else {
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetDataFailure(message)
            }
        }
    }
}