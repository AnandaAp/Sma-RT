package com.maluku.sma_rt.presenter

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.keluarga.CreateKeluargaResponse
import com.maluku.sma_rt.view.viewInterface.TambahKeluargaInterface
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

private const val TAG = "TAMBAH KELUARGA"

@DelicateCoroutinesApi
class TambahKeluargaPresenter(private val activity: Activity, private val view: TambahKeluargaInterface) {
    // Tambah keluarga
    fun addFamilyPresenter(
        token: String,
        nama: String
    ) {
        /*RetrofitService
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
            })*/
        GlobalScope.launch(Dispatchers.Main){
            val response = RetrofitService
                .getService()
                .tambahKeluarga(
                    "Bearer $token",
                    nama
                ).awaitResponse()
            if (response.isSuccessful){
                view.onCreateSuccess("Berhasil menambah daftar keluarga!")
                Toast.makeText(activity,"Pesan: ${response.body()?.message.toString()}", Toast.LENGTH_SHORT).show()
            }
            else {
                view.onCreateFailed("Gagal menambah daftar keluarga!")
                Log.i(TAG, "onFailure: ${response.errorBody()!!}")
            }
        }
    }
}