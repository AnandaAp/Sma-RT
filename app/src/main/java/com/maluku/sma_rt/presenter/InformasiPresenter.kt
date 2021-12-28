package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.*
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "INFORMASI PRESENTER"

class InformasiPresenter(private val view: InformasiInterface) {
    fun createInformasi(
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
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onCreateInformasiFailure(message)
                    }
                }

                override fun onFailure(call: Call<CreateInformasiResponse>, t: Throwable) {
                    view.onCreateInformasiFailure(t.message.toString())
                    Log.i(TAG, "onFailure: ${t.message.toString()}")
                }
            })
    }

    fun getAllInformasi(token: String) {
        RetrofitService
            .getService()
            .getAllInformasi("Bearer $token")
            .enqueue(object : Callback<GetAllInformasiResponse> {
                override fun onResponse(
                    call: Call<GetAllInformasiResponse>,
                    response: Response<GetAllInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                        view.onGetAllInformasiSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetAllInformasiFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.onGetAllInformasiFailure(t.message.toString())
                }
            })
    }

    fun getAllInfoTerkini(token: String) {
        RetrofitService
            .getService()
            .getInfoTerkini("Bearer $token")
            .enqueue(object : Callback<GetAllInformasiResponse> {
                override fun onResponse(
                    call: Call<GetAllInformasiResponse>,
                    response: Response<GetAllInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                        view.onGetInfoTerkiniSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetInfoTerkiniFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.onGetInfoTerkiniFailure(t.message.toString())
                }
            })
    }

    fun getAllKegiatan(token: String) {
        RetrofitService
            .getService()
            .getKegiatan("Bearer $token")
            .enqueue(object : Callback<GetAllInformasiResponse> {
                override fun onResponse(
                    call: Call<GetAllInformasiResponse>,
                    response: Response<GetAllInformasiResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                        view.onGetKegiatanSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetInfoTerkiniFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.onGetKegiatanFailure(t.message.toString())
                }
            })
    }

    fun getInformasiById(
        token: String,
        idInformasi: String
    ) {
        RetrofitService
            .getService()
            .getInformasiByID(
                "Bearer $token",
                idInformasi
            )
            .enqueue(object : Callback<GetInformasiByIDResponse> {
                override fun onResponse(
                    call: Call<GetInformasiByIDResponse>,
                    response: Response<GetInformasiByIDResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getInformasiById as GetInformasiById
                        view.onGetInformasiSuccess(result)
                    } else{
                        val jObjError = JSONObject(response.errorBody()?.string())
                        val message = jObjError.getString("message")
                        view.onGetInformasiFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetInformasiByIDResponse>, t: Throwable) {
                    view.onGetInformasiFailure(t.message.toString())
                }
            })
    }
}