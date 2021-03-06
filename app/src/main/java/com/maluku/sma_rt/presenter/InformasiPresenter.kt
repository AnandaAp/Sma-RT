package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.informasi.*
import com.maluku.sma_rt.view.viewInterface.InformasiInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

private const val TAG = "INFORMASI PRESENTER"

@DelicateCoroutinesApi
class InformasiPresenter(private val view: InformasiInterface) {
    fun createInformasi(
        token: String,
        judul: String,
        kategori: String,
        lokasi: String,
        detail: String,
        gambar: String
    ) {
        /*RetrofitService
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
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .createInformasi(
                    "Bearer $token",
                    judul,
                    gambar,
                    detail,
                    kategori,
                    lokasi
                ).awaitResponse()
            if (response.isSuccessful){
                view.onCreateInformasiSuccess("Berhasil menambah informasi!")
            } else {
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onCreateInformasiFailure(message)
            }
        }
    }

    fun getAllInformasi(token: String) {
        /*RetrofitService
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
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val message = jObjError.getString("message")
                        view.onGetAllInformasiFailure(message)
                    }
                }

                override fun onFailure(call: Call<GetAllInformasiResponse>, t: Throwable) {
                    view.onGetAllInformasiFailure(t.message.toString())
                }
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getAllInformasi("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                view.onGetAllInformasiSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetAllInformasiFailure(message)
            }
        }

    }

    fun getAllInfoTerkini(token: String) {
        /*RetrofitService
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
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getInfoTerkini("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                view.onGetInfoTerkiniSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetInfoTerkiniFailure(message)
            }
        }
    }

    fun getAllKegiatan(token: String) {
        /*RetrofitService
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
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getKegiatan("Bearer $token")
                .awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getAllInformasi as List<GetAllInformasiItem>
                view.onGetKegiatanSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetInfoTerkiniFailure(message)
            }
        }
    }

    fun getInformasiById(
        token: String,
        idInformasi: String
    ) {
        /*RetrofitService
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
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .getInformasiByID(
                    "Bearer $token",
                    idInformasi
                ).awaitResponse()
            if (response.isSuccessful){
                val result = response.body()?.getInformasiById as GetInformasiById
                view.onGetInformasiSuccess(result)
            } else{
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onGetInformasiFailure(message)
            }
        }
    }

    fun updateInformasi(
        token: String,
        id: String,
        judul: String,
        kategori: String,
        lokasi: String,
        detail: String,
        gambar: String
    ) {
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .updateInformasi(
                    "Bearer $token",
                    id,
                    judul,
                    gambar,
                    detail,
                    kategori,
                    lokasi
                ).awaitResponse()
            if (response.isSuccessful){
                view.onUpdateInformasiSuccess("Berhasil memperbaharui informasi!")
            } else {
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onUpdateInformasiFailure(message)
            }
        }
    }

    fun deleteInformasi(
        token: String,
        id: String
    ) {
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .deleteInformasi(
                    "Bearer $token",
                    id
                ).awaitResponse()
            if (response.isSuccessful){
                view.onDeleteInformasiSuccess("Berhasil menghapus informasi!")
            } else {
                val jObjError = JSONObject(response.errorBody()!!.string())
                val message = jObjError.getString("message")
                view.onDeleteInformasiFailure(message)
            }
        }
    }
}