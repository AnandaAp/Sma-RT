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
import com.maluku.sma_rt.view.viewInterface.EditProdukInterface
import com.maluku.sma_rt.view.viewInterface.WargaEditProfileInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "EDIT PROFILE"

class WargaEditProfilePresenter(private val view: WargaEditProfileInterface) {

    fun getDataLogin(token: String) {
        RetrofitService
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
                        response.body()?.message?.let { view.onGetDataFailed(it) }
//                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<WargaGetDataLoginResponse>, t: Throwable) {
                    view.onGetDataFailed("failed")
                }
            })
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
        RetrofitService
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
                        view.onUpdateFailure("Gagal memperbaharui data!")
                    }
//                    Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UpdateWargaResponse>, t: Throwable) {
                    view.onUpdateFailure("Gagal memperbaharui data!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}