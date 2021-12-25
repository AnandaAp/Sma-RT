package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.produk.CreateProductResponse
import com.maluku.sma_rt.model.produk.DeleteProductByIDResponse
import com.maluku.sma_rt.model.produk.UpdateProductByIDResponse
import com.maluku.sma_rt.view.viewInterface.EditProdukInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "EDIT PRODUK"

class WargaTokoEditProdukPresenter(private val activity: Activity, private val view: EditProdukInterface) {

    fun updateProduk(
        token: String,
        produkId: String,
        nama: String,
        detail: String,
        gambar: String,
        harga: String,
        tersedia: String
    ) {
        RetrofitService
            .getService()
            .updateProductByID(
                "Bearer $token",
                produkId,
                nama,
                detail,
                gambar,
                harga,
                tersedia
            )
            .enqueue(object : Callback<UpdateProductByIDResponse> {
                override fun onResponse(
                    call: Call<UpdateProductByIDResponse>,
                    response: Response<UpdateProductByIDResponse>
                ) {
                    if (response.isSuccessful){
                        view.onUpdateSuccess("Berhasil mengubah produk!")
                    }
//                    Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<UpdateProductByIDResponse>, t: Throwable) {
                    view.onUpdateFailure("Gagal mengubah produk!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun hapusProduk(
        token: String,
        produkId: String
    ) {
        RetrofitService
            .getService()
            .deleteProductByID(
                "Bearer $token",
                produkId
            )
            .enqueue(object : Callback<DeleteProductByIDResponse> {
                override fun onResponse(
                    call: Call<DeleteProductByIDResponse>,
                    response: Response<DeleteProductByIDResponse>
                ) {
                    if (response.isSuccessful){
                        view.onDeleteSuccess("Berhasil menghapus produk!")
                    }
                    Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<DeleteProductByIDResponse>, t: Throwable) {
                    view.onDeleteFailure("Gagal menghapus produk!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}