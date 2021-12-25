package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.CreateInformasiResponse
import com.maluku.sma_rt.view.viewInterface.BagikanInformasiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "InformasiPresenter"

class BagikanInformasiPresenter(private val activity: Activity, private val view: BagikanInformasiInterface) {
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
                kategori,
                lokasi,
                detail,
                gambar
            )
            .enqueue(object : Callback<CreateInformasiResponse> {
                override fun onResponse(
                    call: Call<CreateInformasiResponse>,
                    response: Response<CreateInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(activity, "Ini bagikan informasi presenter",Toast.LENGTH_LONG).show()
                        view.onCreateInformasiSuccess("Berhasil menambah informasi!")
                    } else {
                        view.onCreateInformasiFailed("Gagal menambah informasi!")
                    }
                }

                override fun onFailure(call: Call<CreateInformasiResponse>, t: Throwable) {
                    view.onCreateInformasiFailed("Gagal menambah informasi!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}