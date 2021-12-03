package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.CreateKeluargaResponse
import com.maluku.sma_rt.view.viewInterface.TambahKeluargaInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TAMBAH KELUARGA"

class TambahKeluargaPresenter(private val activity: Activity, private val view: TambahKeluargaInterface) {
    // Tambah keluarga
    fun addFamilyPresenter(
        token: String,
        nama: String
    ) {
        RetrofitService
            .getService()
            .tambahKeluarga(
                "Bearer $token",
                nama
            )
            .enqueue(object : Callback<CreateKeluargaResponse> {
                override fun onResponse(
                    call: Call<CreateKeluargaResponse>,
                    response: Response<CreateKeluargaResponse>
                ) {
                    if (response.isSuccessful){
                        view.onCreateSuccess("Berhasil menambah daftar keluarga!")
                    }
                    Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<CreateKeluargaResponse>, t: Throwable) {
                    view.onCreateFailed("Gagal menambah daftar keluarga!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}