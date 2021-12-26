package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.CreateInformasiResponse
import com.maluku.sma_rt.model.informasi.GetAllInformasiItem
import com.maluku.sma_rt.model.informasi.GetAllInformasiResponse
import com.maluku.sma_rt.view.viewInterface.CreateInformasiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "InformasiPresenter"

class CreateInformasiPresenter(private val activity: Activity, private val view: CreateInformasiInterface) {
    fun createInformasiPresenter(
        token: String,
        judul: String,
        kategori: String,
        lokasi: String,
        detail: String,
        gambar: String
    ) {
        RetrofitService
            .getService()
            .createInformasi(
                "Bearer $token",
                judul,
                gambar,
                detail,
                kategori,
                lokasi
            )
            .enqueue(object : Callback<CreateInformasiResponse> {
                override fun onResponse(
                    call: Call<CreateInformasiResponse>,
                    response: Response<CreateInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        view.onCreateInformasiSuccess("Berhasil menambah informasi!")
                    } else {
                        view.onCreateInformasiFailed("Gagal menambah informasi!")
                    }
                }

                override fun onFailure(call: Call<CreateInformasiResponse>, t: Throwable) {
                    view.onCreateInformasiFailed("Gagal menambah informasi!")
                    Log.i(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }
}