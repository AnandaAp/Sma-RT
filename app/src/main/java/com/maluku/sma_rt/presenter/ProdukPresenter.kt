package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaItem
import com.maluku.sma_rt.model.keluarga.GetAllProdukKeluargaResponse
import com.maluku.sma_rt.model.produk.CreateProductResponse
import com.maluku.sma_rt.model.produk.DeleteProductByIDResponse
import com.maluku.sma_rt.model.produk.UpdateProductByIDResponse
import com.maluku.sma_rt.view.viewInterface.ProdukInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PRODUK PRESENTER"

class ProdukPresenter(private val view: ProdukInterface) {

    fun getListProdukByToken(token: String) {
        RetrofitService
            .getService()
            .getAllProdukKeluarga("Bearer $token")
            .enqueue(object : Callback<GetAllProdukKeluargaResponse> {
                override fun onResponse(
                    call: Call<GetAllProdukKeluargaResponse>,
                    response: Response<GetAllProdukKeluargaResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllProdukKeluarga as List<GetAllProdukKeluargaItem>
                        view.onGetAllDataSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllDataFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllProdukKeluargaResponse>, t: Throwable) {
                    view.onGetAllDataFailure("Error")
                }
            })
    }

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
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onCreateFailure(message)
                    }
                }

                override fun onFailure(call: Call<CreateProductResponse>, t: Throwable) {
                    view.onCreateFailure("Gagal menambah produk!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }

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
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onUpdateFailure(message)
                    }
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
                    } else {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onDeleteFailure(message)
                    }
                }

                override fun onFailure(call: Call<DeleteProductByIDResponse>, t: Throwable) {
                    view.onDeleteFailure("Gagal menghapus produk!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}