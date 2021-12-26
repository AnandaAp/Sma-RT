package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetKeluargaSayaResponse
import com.maluku.sma_rt.model.keluarga.UpdateKeluargaResponse
import com.maluku.sma_rt.model.produk.UpdateProductByIDResponse
import com.maluku.sma_rt.view.viewInterface.KelolaTokoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "KELOLA TOKO PRESENTER"

class WargaKelolaTokoPresenter(private val activity: Activity, private var view: KelolaTokoInterface) {
    fun getDataKeluargaSaya(token: String) {
        RetrofitService
            .getService()
            .getKeluargaSaya("Bearer $token")
            .enqueue(object : Callback<GetKeluargaSayaResponse> {
                override fun onResponse(
                    call: Call<GetKeluargaSayaResponse>,
                    response: Response<GetKeluargaSayaResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getKeluargaSaya
                        view.onGetDataSuccess(result)
//                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(activity,"Pesan: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetKeluargaSayaResponse>, t: Throwable) {
                    view.onGetDataFailed("failed")
                }
            })
    }

    fun updateToko(
        token: String,
        id_keluarga: String,
        nama_toko: String,
        gambar: String
    ) {
        RetrofitService
            .getService()
            .updateToko(
                "Bearer $token",
                id_keluarga,
                nama_toko,
                gambar,
            )
            .enqueue(object : Callback<UpdateKeluargaResponse> {
                override fun onResponse(
                    call: Call<UpdateKeluargaResponse>,
                    response: Response<UpdateKeluargaResponse>
                ) {
                    if (response.isSuccessful){
                        view.onUpdateSuccess("Berhasil mengubah produk!")
                    }
//                    Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UpdateKeluargaResponse>, t: Throwable) {
                    view.onUpdateFailure("Gagal mengubah produk!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}