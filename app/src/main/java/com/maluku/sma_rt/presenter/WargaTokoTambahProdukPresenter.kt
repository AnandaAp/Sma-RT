package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.produk.CreateProductResponse
import com.maluku.sma_rt.view.viewInterface.TambahProdukInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TAMBAH PRODUK"

class WargaTokoTambahProdukPresenter(private val activity: Activity, private val view: TambahProdukInterface) {

    fun tambahProduk(
        token: String,
        nama: String,
        detail: String,
        gambar: String,
        harga: String
    ) {
        RetrofitService
            .getService()
            .createProduct(
                "Bearer $token",
                nama,
                detail,
                gambar,
                harga
            )
            .enqueue(object : Callback<CreateProductResponse> {
                override fun onResponse(
                    call: Call<CreateProductResponse>,
                    response: Response<CreateProductResponse>
                ) {
                    if (response.isSuccessful){
                        view.onCreateSuccess("Berhasil menambah produk!")
                    }
                    Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<CreateProductResponse>, t: Throwable) {
                    view.onCreateFailed("Gagal menambah produk!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}