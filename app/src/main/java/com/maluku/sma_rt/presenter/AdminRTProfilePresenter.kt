package com.maluku.sma_rt.presenter

import android.util.Log
import com.maluku.sma_rt.api.RetrofitService
import com.maluku.sma_rt.model.pengurus.DefaultPengurusResponse
import com.maluku.sma_rt.model.pengurus.GetDataPengurusByLoginResponse
import com.maluku.sma_rt.model.warga.UpdateWargaResponse
import com.maluku.sma_rt.view.viewInterface.AdminRTProfileInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "PROFIL ADMIN"
class AdminRTProfilePresenter(private val view: AdminRTProfileInterface) {
    fun getDataLoginPengurus(token: String) {
        RetrofitService
            .getService()
            .getDataLoginPengurus("Bearer $token")
            .enqueue(object : Callback<GetDataPengurusByLoginResponse> {
                override fun onResponse(
                    call: Call<GetDataPengurusByLoginResponse>,
                    response: Response<GetDataPengurusByLoginResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.getPengurusById
                        view.onGetDataSuccess(result)
                    } else{
                        response.body()?.message?.let { view.onGetDataFailed(it) }
                    }
                }
                override fun onFailure(call: Call<GetDataPengurusByLoginResponse>, t: Throwable) {
                    view.onGetDataFailed(t.message.toString())
                }
            })
    }

    fun updateProfilePengurus(
        token: String,
        pengurusId: String,
        gender: String,
        no_hp: String,
        nama: String,
        email: String,
        gambar: String,
        kodeRT: String
    ) {
        RetrofitService
            .getService()
            .updatePengurus(
                "Bearer $token",
                pengurusId,
                gender,
                no_hp,
                nama,
                email,
                gambar,
                kodeRT
            )
            .enqueue(object : Callback<DefaultPengurusResponse> {
                override fun onResponse(
                    call: Call<DefaultPengurusResponse>,
                    response: Response<DefaultPengurusResponse>
                ) {
                    if (response.isSuccessful){
                        view.onUpdateSuccess("Berhasil memperbaharui data!")
                    } else {
                        view.onUpdateFailure("Gagal memperbaharui data!")
                    }
                }

                override fun onFailure(call: Call<DefaultPengurusResponse>, t: Throwable) {
                    view.onUpdateFailure("Gagal memperbaharui data!")
                    Log.i(TAG, "onFailure: ${t.message}")
                }
            })
    }
}